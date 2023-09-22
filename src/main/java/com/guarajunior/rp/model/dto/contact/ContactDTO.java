package com.guarajunior.rp.model.dto.contact;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class ContactDTO {
	private String email;
    private String telephone;
    private String cell_phone;
    @JsonIgnore
    private UUID idCompany;
}
