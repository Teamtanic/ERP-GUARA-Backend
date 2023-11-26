package com.guarajunior.guararp.infra.repository;

import com.guarajunior.guararp.infra.model.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DocumentTypeRepository extends JpaRepository<DocumentType, UUID> {
}
