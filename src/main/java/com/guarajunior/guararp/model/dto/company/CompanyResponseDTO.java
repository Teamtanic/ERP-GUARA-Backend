package com.guarajunior.guararp.model.dto.company;

import com.guarajunior.guararp.model.dto.companyrelationship.CompanyRelationshipDTO;
import com.guarajunior.guararp.model.dto.contact.ContactDTO;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class CompanyResponseDTO {
	private UUID id;
    private String name;
    private String cpf;
    private String cnpj;
    private List<CompanyRelationshipDTO> companyRelationships;
    private ContactDTO contact;

}
