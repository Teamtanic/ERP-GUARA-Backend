package com.guarajunior.guararp.infra.repository;

import com.guarajunior.guararp.infra.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {
    Page<Project> findAllByActive(Boolean active, Pageable pageable);
}
