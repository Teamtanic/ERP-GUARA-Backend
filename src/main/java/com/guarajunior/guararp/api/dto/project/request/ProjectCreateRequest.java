package com.guarajunior.guararp.api.dto.project.request;

import com.guarajunior.guararp.api.dto.project.UserRoleDTO;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ProjectCreateRequest {
    private String description;
    private String title;
    private List<UUID> companyRelationshipIds;
    private UUID offeringId;
    private List<UserRoleDTO> users;
}
