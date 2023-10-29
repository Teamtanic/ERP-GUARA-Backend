package com.guarajunior.guararp.repository;

import com.guarajunior.guararp.model.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DepartmentRepository extends JpaRepository<Department, UUID> {
	Page<Department> findAll(Pageable pageable);
}
