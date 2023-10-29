package com.guarajunior.guararp.model.dto.company;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CompanyUpdateDTO {
	@NotBlank(message = "O campo 'Nome' é obrigatório.")
    private String name;

	private String cpf;
    private String cnpj;
    
    
}
