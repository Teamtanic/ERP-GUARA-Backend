package com.guarajunior.guararp.repository;

import com.guarajunior.guararp.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {
	@Query("SELECT p FROM Project p WHERE p.active = true")
	Page<Project> findAll(Pageable pageable);
}
