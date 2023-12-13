package com.guarajunior.guararp.infra.repository;

import com.guarajunior.guararp.infra.model.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DocumentRepository extends JpaRepository<Document, UUID> {
    List<Document> findAllByAlfrescoIdIn(List<String> uuids);
    Optional<Document> findByAlfrescoId(String alfrescoId);
}
