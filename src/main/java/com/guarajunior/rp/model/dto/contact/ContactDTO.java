package com.guarajunior.rp.model.dto.contact;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.guarajunior.rp.model.Company;
import com.guarajunior.rp.model.User;

import lombok.Data;

@Data
public class ContactDTO {
	private String email;
    private String telephone;
    private String cell_phone;
    @JsonIgnore
    private Company company;
    @JsonIgnore
    private User user;
}
