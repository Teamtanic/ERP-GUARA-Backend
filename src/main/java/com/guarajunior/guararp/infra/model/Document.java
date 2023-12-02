package com.guarajunior.guararp.infra.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class Document {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @ManyToOne
    private DocumentType documentType;

    private String alfrescoId;

    @ManyToOne
    @JsonIgnore
    @NotNull
    private Project project;

    public Document() {
    }

    public Document(DocumentType documentType, String alfrescoId, Project project) {
        this.documentType = documentType;
        this.alfrescoId = alfrescoId;
        this.project = project;
    }
}
