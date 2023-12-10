package com.guarajunior.guararp.api.dto.role.response;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class RoleResponse {
    private UUID id;
    private String name;
    private List<RolePermissionResponse> rolePermissions;
}
