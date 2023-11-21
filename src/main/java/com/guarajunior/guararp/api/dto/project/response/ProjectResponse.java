package com.guarajunior.guararp.api.dto.project.response;

import com.guarajunior.guararp.api.dto.companyrelationship.response.CompanyRelationshipProjectResponse;
import com.guarajunior.guararp.api.dto.offering.response.OfferingResponse;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class ProjectResponse {
	private String title;
	private String description;
	private CompanyRelationshipProjectResponse companyRelationship;
	private Set<OfferingResponse> offerings;
	private List<UserRoleResponse> users;
}
