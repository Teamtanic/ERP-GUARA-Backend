package com.guarajunior.guararp.api.dto.department.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DepartmentCreateRequest {
    @NotNull
    private String name;
}
