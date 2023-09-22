package com.guarajunior.rp.model.dto.companyrelationship;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.guarajunior.rp.enums.BusinessRelationshipType;
import com.guarajunior.rp.model.Company;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CompanyRelationshipResponseDTO {
	private UUID id;
	
	@NotBlank(message = "O campo 'Relacionamento' é obrigatório.")
	private BusinessRelationshipType businessRelationshipType;
	
	@NotBlank()
	@JsonIgnore	
	private Company company;
}
