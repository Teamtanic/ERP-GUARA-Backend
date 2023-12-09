package com.guarajunior.guararp.api.dto.role.request;

import com.guarajunior.guararp.infra.model.Permission;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class RoleCreateRequest {
    @NotNull
    private String name;
    private List<Permission> permissions;
}
