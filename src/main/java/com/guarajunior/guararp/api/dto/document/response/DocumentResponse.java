package com.guarajunior.guararp.api.dto.document.response;

import com.guarajunior.guararp.infra.model.DocumentType;
import lombok.Data;
import org.alfresco.core.model.Node;

import java.util.UUID;

@Data
public class DocumentResponse {
    private UUID id;
    private DocumentType documentType;
    private String alfrescoId;
}
