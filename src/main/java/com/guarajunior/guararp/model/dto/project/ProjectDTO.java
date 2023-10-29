package com.guarajunior.guararp.model.dto.project;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ProjectDTO {
	private String description;
	private List<UUID> companyRelationshipIds;
	private UUID offeringId;
	private List<UserRoleDTO> users;
}
