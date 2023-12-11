package com.guarajunior.guararp.api.dto.companyrelationship.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.guarajunior.guararp.api.dto.company.response.CompanyResponse;
import com.guarajunior.guararp.infra.enums.BusinessRelationshipType;
import com.guarajunior.guararp.infra.model.Company;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class CompanyRelationshipProjectResponse {
	private UUID idCompanyRelationship;
	private BusinessRelationshipType businessRelationship;
	private CompanyResponse company;
}
