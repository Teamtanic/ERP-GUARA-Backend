package com.guarajunior.rp.model.dto.company;

import java.util.List;
import java.util.UUID;

import com.guarajunior.rp.model.dto.companyrelationship.CompanyRelationshipDTO;
import com.guarajunior.rp.model.dto.contact.ContactDTO;

import lombok.Data;

@Data
public class CompanyResponseDTO {
	private UUID id;
    private String name;
    private String cpf;
    private String cnpj;
    private List<CompanyRelationshipDTO> companyRelationships;
    private ContactDTO contact;

}
