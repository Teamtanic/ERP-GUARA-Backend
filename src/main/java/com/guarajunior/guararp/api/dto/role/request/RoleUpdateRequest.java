package com.guarajunior.guararp.api.dto.role.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RoleUpdateRequest {
    @NotNull
    private String name;
}
