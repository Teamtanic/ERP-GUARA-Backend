package com.guarajunior.guararp.domain.service;

import com.guarajunior.guararp.api.dto.documenttype.request.DocumentTypeRequest;
import com.guarajunior.guararp.api.dto.documenttype.response.DocumentTypeResponse;
import com.guarajunior.guararp.api.error.exception.EntityNotFoundException;
import com.guarajunior.guararp.infra.model.DocumentType;
import com.guarajunior.guararp.infra.repository.DocumentTypeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentTypeService {
    private final DocumentTypeRepository documentTypeRepository;
    private final ModelMapper mapper;

    public DocumentTypeResponse create(DocumentTypeRequest documentTypeRequest) {
        DocumentType documentType = mapper.map(documentTypeRequest, DocumentType.class);
        return mapper.map(documentTypeRepository.save(documentType), DocumentTypeResponse.class);
    }

    public List<DocumentTypeResponse> getAll() {
        List<DocumentType> all = documentTypeRepository.findAll();
        return all.stream().map(documentType -> mapper.map(documentType, DocumentTypeResponse.class)).toList();
    }

    public DocumentTypeResponse getById(UUID uuid) {
        DocumentType documentType = documentTypeRepository.findById(uuid).orElseThrow(() -> new EntityNotFoundException("DocumentType não encontrado"));
        return mapper.map(documentType, DocumentTypeResponse.class);
    }

    public DocumentTypeResponse update(UUID uuid, DocumentTypeRequest documentTypeRequest) {
        DocumentType documentType = documentTypeRepository.findById(uuid).orElseThrow(() -> new EntityNotFoundException("DocumentType não encontrado"));

        mapper.map(documentTypeRequest, documentType);

        return mapper.map(documentTypeRepository.save(documentType), DocumentTypeResponse.class);
    }

    public void delete(UUID uuid) {
        DocumentType documentType = documentTypeRepository.findById(uuid).orElseThrow(() -> new EntityNotFoundException("DocumentType não encontrado"));
        documentTypeRepository.delete(documentType);
    }
}
