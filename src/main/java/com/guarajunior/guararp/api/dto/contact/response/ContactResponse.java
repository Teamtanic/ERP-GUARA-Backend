package com.guarajunior.guararp.api.dto.contact.response;


import lombok.Data;

import java.util.UUID;

@Data
public class ContactResponse {
    private UUID id;
    private String email;
    private String telephone;
    private String cell_phone;
}
