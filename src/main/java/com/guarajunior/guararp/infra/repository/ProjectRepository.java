package com.guarajunior.guararp.infra.repository;

import com.guarajunior.guararp.infra.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {
    Page<Project> findAllByActive(Boolean active, Pageable pageable);
    @Query("SELECT DISTINCT p FROM Project p JOIN p.companyRelationships cr WHERE cr.company.id = :companyId")
    Page<Project> findAllByCompanyId(UUID companyId, Pageable pageable);
}
