package com.guarajunior.rp.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.guarajunior.rp.model.UserPrivilege;

public interface UserPrivilegeRepository extends JpaRepository<UserPrivilege, UUID> {
	Page<UserPrivilege> findAll(Pageable pageable);
}
