package com.guarajunior.guararp.api.dto.contact.response;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.guarajunior.guararp.infra.model.Company;
import lombok.Data;

@Data
public class ContactResponse {
	private String email;
    private String telephone;
    private String cell_phone;
    @JsonIgnore
    private Company company;
}
