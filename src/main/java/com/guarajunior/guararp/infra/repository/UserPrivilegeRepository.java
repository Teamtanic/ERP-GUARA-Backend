package com.guarajunior.guararp.infra.repository;

import com.guarajunior.guararp.infra.model.UserPrivilege;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserPrivilegeRepository extends JpaRepository<UserPrivilege, UUID> {
	Page<UserPrivilege> findAll(Pageable pageable);
}
