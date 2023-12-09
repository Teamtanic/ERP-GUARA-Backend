package com.guarajunior.guararp.api.dto.role.response;

import com.guarajunior.guararp.infra.model.Permission;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class RoleResponse {
    private UUID id;
    private String name;
    private List<Permission> permissions;
}
