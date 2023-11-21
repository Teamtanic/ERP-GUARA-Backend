package com.guarajunior.guararp.api.dto.project.request;

import com.guarajunior.guararp.api.dto.project.UserRoleDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ProjectCreateRequest {
    private String description;
    private String title;
    private List<UUID> companyRelationshipIds;
    @NotNull
    private List<UUID> offeringIds;
    private List<UserRoleDTO> users;
}
