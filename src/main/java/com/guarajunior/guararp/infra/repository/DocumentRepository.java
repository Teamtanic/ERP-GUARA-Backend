package com.guarajunior.guararp.infra.repository;

import com.guarajunior.guararp.infra.model.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DocumentRepository extends JpaRepository<Document, UUID> {
    Page<Document> findAllByProjectId(UUID projectId, Pageable pageable);
    List<Document> findAllByAlfrescoIdIn(List<String> uuids);
}
