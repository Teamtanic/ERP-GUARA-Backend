package com.guarajunior.rp.model.dto.company;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

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
