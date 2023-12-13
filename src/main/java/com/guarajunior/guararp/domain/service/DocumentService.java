package com.guarajunior.guararp.domain.service;

import com.guarajunior.guararp.api.dto.document.request.DocumentRequest;
import com.guarajunior.guararp.api.dto.document.response.DocumentResponse;
import com.guarajunior.guararp.api.dto.document.response.NodeResponse;
import com.guarajunior.guararp.api.error.exception.EntityNotFoundException;
import com.guarajunior.guararp.infra.model.Document;
import com.guarajunior.guararp.infra.model.DocumentType;
import com.guarajunior.guararp.infra.model.Project;
import com.guarajunior.guararp.infra.repository.DocumentRepository;
import com.guarajunior.guararp.infra.repository.DocumentTypeRepository;
import com.guarajunior.guararp.infra.repository.ProjectRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alfresco.core.handler.NodesApi;
import org.alfresco.core.handler.SitesApi;
import org.alfresco.core.model.*;
import org.apache.commons.io.IOUtils;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentService {
    private final ModelMapper mapper;
    private final NodesApi nodesApi;
    private final SitesApi sitesApi;
    private final DocumentRepository documentRepository;
    private final DocumentTypeRepository documentTypeRepository;

    public NodeResponse getDocumentById(String alfrescoId) {
        Document document = documentRepository.findByAlfrescoId(alfrescoId).orElseThrow(() -> new EntityNotFoundException("Documento não encontrado"));
        Node node = Objects.requireNonNull(nodesApi.getNode(document.getAlfrescoId(), null, null, null).getBody()).getEntry();

        NodeResponse nodeResponse = mapper.map(node, NodeResponse.class);
        nodeResponse.setDocument(mapper.map(document, DocumentResponse.class));

        return nodeResponse;
    }

    public NodeResponse handleFileUpload(DocumentRequest documentRequest) {
        Node newFile = uploadFile(
                getSiteNodeId(),
                documentRequest.getFile().getOriginalFilename(),
                documentRequest.getTitle(),
                documentRequest.getDescription(),
                documentRequest.getFolderPath(),
                documentRequest.getFile()
        );

        DocumentType documentType = documentTypeRepository.findById(documentRequest.getDocumentTypeId()).orElseThrow(() -> new EntityNotFoundException("DocumentType não encontrado"));

        Document document = new Document(documentType, Objects.requireNonNull(newFile).getId());
        documentRepository.save(document);

        NodeResponse nodeResponse = mapper.map(newFile, NodeResponse.class);
        nodeResponse.setDocument(mapper.map(document, DocumentResponse.class));

        return nodeResponse;
    }

    public List<NodeResponse> listFolderItems(String folderPath) {
        NodeChildAssociationPagingList nodeList = Objects.requireNonNull(nodesApi.listNodeChildren(getSiteNodeId(), null, null, null, null, null, folderPath, null, null).getBody()).getList();

        List<String> nodeIds = nodeList.getEntries().stream().map(node -> node.getEntry().getId()).toList();
        List<Document> documents = documentRepository.findAllByAlfrescoIdIn(nodeIds);

        return nodeList.getEntries().stream().map(node -> {
            NodeChildAssociation entry = node.getEntry();
            NodeResponse nodeResponse = mapper.map(entry, NodeResponse.class);

            Optional<Document> optionalDocument = documents.stream()
                    .filter(document -> document.getAlfrescoId().equals(entry.getId()))
                    .findFirst();

            optionalDocument.ifPresent(document -> nodeResponse.setDocument(mapper.map(document, DocumentResponse.class)));

            return nodeResponse;
        }).toList();
    }

    private String getSiteNodeId() {
        Site site = Objects.requireNonNull(sitesApi.getSite("guararp", null, null).getBody()).getEntry();
        NodeChildAssociationPaging nodeChildAssociationPaging = nodesApi.listNodeChildren(site.getGuid(), null, null, null, null, null, null, null, null).getBody();
        return Objects.requireNonNull(nodeChildAssociationPaging).getList().getEntries().get(0).getEntry().getId();
    }

    private Node uploadFile(
            String parentFolderId,
            String fileName,
            String title,
            String description,
            String relativeFolderPath,
            MultipartFile file
    ) {
        Node fileNode = createFileMetadata(parentFolderId, fileName, title, description, relativeFolderPath);

        try (InputStream inputStream = file.getInputStream()) {
            Node updatedFileNode = Objects.requireNonNull(nodesApi
                            .updateNodeContent(fileNode.getId(), IOUtils.toByteArray(inputStream), null, null, null, null, null)
                            .getBody())
                    .getEntry();

            log.info("Created file with content: {}", updatedFileNode.toString());

            return updatedFileNode;
        } catch (IOException e) {
            log.error("Error uploading file content", e);
            return null;
        }
    }

    private Node createFileMetadata(String parentFolderId, String fileName, String title, String description,
                                    String relativeFolderPath) {
        List<String> fileAspects = new ArrayList<>();
        fileAspects.add("cm:titled");

        Map<String, String> fileProps = new HashMap<>();
        fileProps.put("cm:title", title);
        fileProps.put("cm:description", description);

        NodeBodyCreate nodeBodyCreate = new NodeBodyCreate();
        nodeBodyCreate.setName(fileName);
        nodeBodyCreate.setNodeType("cm:content");
        nodeBodyCreate.setAspectNames(fileAspects);
        nodeBodyCreate.setProperties(fileProps);
        nodeBodyCreate.setRelativePath(relativeFolderPath);

        // Create the file node metadata
        return Objects.requireNonNull(nodesApi
                .createNode(parentFolderId, nodeBodyCreate, null, null, null, null, null)
                .getBody()).getEntry();
    }

    public ResponseEntity<Resource> getDocumentContentById(String uuid) {
        return nodesApi.getNodeContent(uuid, null, null, null);
    }


}
