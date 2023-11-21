package com.guarajunior.guararp.api.dto.project.request;

import com.guarajunior.guararp.api.dto.project.UserRoleDTO;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ProjectUpdateRequest {
    private String description;
    private String title;
    private Boolean active;
    private List<UUID> companyRelationshipIds;
    private List<UserRoleDTO> users;
}
