package com.guarajunior.guararp.api.dto.company.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class CompanyCreateRequest {
	@NotBlank(message = "O campo 'Nome' é obrigatório.")
    private String name;
	
	private String cpf;
    private String cnpj;
    
    private String email;
    private String telephone;
    private String cell_phone;
    
    private List<String> businessRelationshipType;
}
