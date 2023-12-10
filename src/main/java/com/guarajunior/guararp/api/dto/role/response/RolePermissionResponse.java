package com.guarajunior.guararp.api.dto.role.response;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class RolePermissionResponse {
    private UUID id;
    private UUID roleId;
    private UUID departmentId;
    private List<String> permissions;
}
