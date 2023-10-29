package com.guarajunior.guararp.api.dto.company.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CompanyUpdateRequest {
	@NotBlank(message = "O campo 'Nome' é obrigatório.")
    private String name;

	private String cpf;
    private String cnpj;
    
    
}
