package com.guarajunior.rp.model.dto.contact;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.guarajunior.rp.model.Company;

import lombok.Data;

@Data
public class ContactResponseDTO {
	private String email;
    private String telephone;
    private String cell_phone;
    @JsonIgnore
    private Company company;
}
