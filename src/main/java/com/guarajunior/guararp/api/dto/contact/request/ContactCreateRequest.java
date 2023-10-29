package com.guarajunior.guararp.api.dto.contact.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.guarajunior.guararp.infra.model.Company;
import com.guarajunior.guararp.infra.model.User;
import lombok.Data;

@Data
public class ContactCreateRequest {
	private String email;
    private String telephone;
    private String cell_phone;
    @JsonIgnore
    private Company company;
    @JsonIgnore
    private User user;
}
