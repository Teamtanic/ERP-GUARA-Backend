package com.guarajunior.guararp.api.dto.company.response;

import com.guarajunior.guararp.api.dto.companyrelationship.response.CompanyRelationshipResponse;
import com.guarajunior.guararp.api.dto.contact.response.ContactResponse;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class CompanyResponse {
    private UUID id;
    private String name;
    private String cpf;
    private String cnpj;
    private List<CompanyRelationshipResponse> companyRelationships;
    private List<ContactResponse> contact;

}
