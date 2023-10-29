package com.guarajunior.guararp.model.dto.project;

import com.guarajunior.guararp.model.dto.companyrelationship.CompanyRelationshipResponseDTO;
import com.guarajunior.guararp.model.dto.offering.OfferingResponseDTO;
import lombok.Data;

import java.util.List;

@Data
public class ProjectResponseDTO {
	private String description;
	private CompanyRelationshipResponseDTO companyRelationship;
	private OfferingResponseDTO offering;
	private List<UserRoleResponseDTO> users;
}
