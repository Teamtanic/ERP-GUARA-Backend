package com.guarajunior.guararp.infra.repository;

import com.guarajunior.guararp.infra.model.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RolePermissionRepository extends JpaRepository<RolePermission, UUID> {

}
