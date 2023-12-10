package com.guarajunior.guararp.api.dto.role.request;

import com.guarajunior.guararp.infra.model.Permission;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class RolePermissionRequest {
    private UUID departmentId;
    private List<Permission> permissions;
}
