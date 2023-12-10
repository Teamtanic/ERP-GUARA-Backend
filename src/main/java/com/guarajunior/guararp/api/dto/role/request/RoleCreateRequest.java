package com.guarajunior.guararp.api.dto.role.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class RoleCreateRequest {
    @NotNull
    private String name;
    private List<RolePermissionRequest> rolePermissions;
}
