package com.guarajunior.rp.model.dto.project;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class ProjectDTO {
	private String description;
	private List<UUID> companyRelationshipIds;
	private UUID offeringId;
	private List<UserRoleDTO> users;
}
