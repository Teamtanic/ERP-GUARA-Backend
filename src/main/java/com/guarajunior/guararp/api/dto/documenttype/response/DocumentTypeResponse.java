package com.guarajunior.guararp.api.dto.documenttype.response;

import lombok.Data;

import java.util.UUID;

@Data
public class DocumentTypeResponse {
    UUID id;
    String name;
    String description;
}
