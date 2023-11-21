package com.guarajunior.guararp.infra.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.guarajunior.guararp.infra.enums.OfferingType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
public class Offering {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private String description;
    private OfferingType type;
    private Boolean active;

    @ManyToMany
    @JsonIgnore
    private Set<Project> projects;

    private UUID createdBy;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdAt;
    private UUID updatedBy;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
}
