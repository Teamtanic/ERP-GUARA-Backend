package com.guarajunior.rp.model.dto.project;

import java.util.List;

import com.guarajunior.rp.model.dto.companyrelationship.CompanyRelationshipResponseDTO;
import com.guarajunior.rp.model.dto.offering.OfferingResponseDTO;

import lombok.Data;

@Data
public class ProjectResponseDTO {
	private String description;
	private CompanyRelationshipResponseDTO companyRelationship;
	private OfferingResponseDTO offering;
	private List<UserRoleResponseDTO> users;
}
