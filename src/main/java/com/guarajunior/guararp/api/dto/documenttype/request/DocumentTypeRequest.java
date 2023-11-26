package com.guarajunior.guararp.api.dto.documenttype.request;

import lombok.Data;

import java.util.UUID;

@Data
public class DocumentTypeRequest {
    private String name;
    private String description;
}
