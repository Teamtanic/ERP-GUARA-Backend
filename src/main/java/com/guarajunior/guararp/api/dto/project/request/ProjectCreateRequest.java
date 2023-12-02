package com.guarajunior.guararp.api.dto.project.request;

import com.guarajunior.guararp.api.dto.project.UserRoleDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ProjectCreateRequest {
    @NotNull @NotBlank
    private String title;
    private String description;
    @NotNull
    private List<UUID> companyRelationshipIds;
    @NotNull
    private List<UUID> offeringIds;
    @NotNull
    private List<UserRoleDTO> users;
}
