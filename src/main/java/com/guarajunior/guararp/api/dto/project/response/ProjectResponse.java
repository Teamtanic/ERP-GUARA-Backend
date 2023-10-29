package com.guarajunior.guararp.api.dto.project.response;

import com.guarajunior.guararp.api.dto.companyrelationship.response.CompanyRelationshipProjectResponse;
import com.guarajunior.guararp.api.dto.offering.response.OfferingResponse;
import lombok.Data;

import java.util.List;

@Data
public class ProjectResponse {
	private String description;
	private CompanyRelationshipProjectResponse companyRelationship;
	private OfferingResponse offering;
	private List<UserRoleResponse> users;
}
