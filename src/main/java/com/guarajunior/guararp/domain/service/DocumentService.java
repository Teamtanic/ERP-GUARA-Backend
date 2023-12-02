package com.guarajunior.guararp.domain.service;

import com.guarajunior.guararp.api.dto.document.request.DocumentRequest;
import com.guarajunior.guararp.api.dto.document.response.DocumentResponse;
import com.guarajunior.guararp.api.error.exception.EntityNotFoundException;
import com.guarajunior.guararp.infra.model.Document;
import com.guarajunior.guararp.infra.model.DocumentType;
import com.guarajunior.guararp.infra.model.Project;
import com.guarajunior.guararp.infra.repository.DocumentRepository;
import com.guarajunior.guararp.infra.repository.DocumentTypeRepository;
import com.guarajunior.guararp.infra.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alfresco.core.handler.NodesApi;
import org.alfresco.core.handler.SitesApi;
import org.alfresco.core.model.*;
import org.apache.commons.io.IOUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentService {
    private final ModelMapper mapper;
    private final NodesApi nodesApi;
    private final SitesApi sitesApi;
    private final DocumentRepository documentRepository;
    private final ProjectRepository projectRepository;
    private final DocumentTypeRepository documentTypeRepository;

    public Page<DocumentResponse> getAllDocuments(UUID projectId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);

        Project projectById = projectRepository.findById(projectId).orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado"));

        String relativePath = "/Projetos/" + projectById.getTitle();

        NodeChildAssociationPagingList list = Objects.requireNonNull(nodesApi.listNodeChildren(getSiteNodeId(), page * size, size, null, null, null, relativePath, null, null).getBody()).getList();

        List<Document> allById = documentRepository.findAllByAlfrescoIdIn(
                list.getEntries()
                        .stream()
                        .map(node -> node.getEntry().getId())
                        .toList()
        );


        List<DocumentResponse> documentResponseList = allById
                .stream()
                .map(document -> {
                    DocumentResponse documentResponse = mapper.map(document, DocumentResponse.class);
                    documentResponse.setDocument(list.getEntries()
                            .stream()
                            .filter(node -> node.getEntry().getId().equals(document.getAlfrescoId()))
                            .findFirst()
                            .orElseThrow()
                            .getEntry()
                    );

                    return documentResponse;
                })
                .toList();

        return new PageImpl<>(documentResponseList, pageable, list.getPagination().getTotalItems());

        /*List<DocumentResponse> documentResponses = allByProjectId.getContent().stream().map(document -> {
            DocumentResponse documentResponse = mapper.map(document, DocumentResponse.class);
            documentResponse.setDocument(
                    list
                            .getEntries()
                            .stream()
                            .filter(nodeChildAssociationEntry ->
                                    nodeChildAssociationEntry.getEntry().getId().equals(documentResponse.getAlfrescoId())
                            ).findFirst().get().getEntry()
            );

            return documentResponse;
        }).toList();*/
    }

    public DocumentResponse getDocumentById(UUID uuid) {
        Document document = documentRepository.findById(uuid).orElseThrow(() -> new EntityNotFoundException("Documento não encontrado"));
        Node node = Objects.requireNonNull(nodesApi.getNode(document.getAlfrescoId(), null, null, null).getBody()).getEntry();

        DocumentResponse documentResponse = mapper.map(document, DocumentResponse.class);
        documentResponse.setDocument(node);

        return documentResponse;
    }

    public DocumentResponse handleFileUpload(UUID projectId, DocumentRequest documentRequest) {
        Project projectById = projectRepository.findById(projectId).orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado"));
        String folderPath = "/Projetos/" + projectById.getTitle();

        Node newFile = uploadFile(
                getSiteNodeId(),
                documentRequest.getFile().getOriginalFilename(),
                documentRequest.getTitle(),
                documentRequest.getDescription(),
                folderPath,
                documentRequest.getFile()
        );

        DocumentType documentType = documentTypeRepository.findById(documentRequest.getDocumentTypeId()).orElseThrow(() -> new EntityNotFoundException("DocumentType não encontrado"));

        Document document = new Document(documentType, Objects.requireNonNull(newFile).getId(), projectById);
        documentRepository.save(document);

        DocumentResponse documentResponse = mapper.map(document, DocumentResponse.class);
        documentResponse.setDocument(newFile);

        return documentResponse;
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

}
