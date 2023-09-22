package com.guarajunior.rp.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.guarajunior.rp.model.Department;

public interface DepartmentRepository extends JpaRepository<Department, UUID> {
	Page<Department> findAll(Pageable pageable);
}
