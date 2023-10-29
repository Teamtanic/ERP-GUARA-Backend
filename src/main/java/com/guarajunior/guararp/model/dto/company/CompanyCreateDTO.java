package com.guarajunior.guararp.model.dto.company;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class CompanyCreateDTO {
	@NotBlank(message = "O campo 'Nome' é obrigatório.")
    private String name;
	
	private String cpf;
    private String cnpj;
    
    private String email;
    private String telephone;
    private String cell_phone;
    
    private List<String> businessRelationshipType;
}
