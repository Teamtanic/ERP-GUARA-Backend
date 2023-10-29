package com.guarajunior.guararp.model.dto.companyrelationship;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.guarajunior.guararp.enums.BusinessRelationshipType;
import com.guarajunior.guararp.model.Company;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class CompanyRelationshipDTO {
	private UUID id;
	private Boolean active;
	
	@NotBlank(message = "O campo 'Relacionamento' é obrigatório.")
	private BusinessRelationshipType businessRelationshipType;
	
	@NotBlank()
	@JsonIgnore	
	private Company company;
}
