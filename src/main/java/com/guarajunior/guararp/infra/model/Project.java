package com.guarajunior.guararp.infra.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
public class Project {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private String title;
    private String description;
    private Boolean status;
    private Boolean active;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Offering> offerings;

    private UUID createdBy;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdAt;
    private UUID updatedBy;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @OneToMany(mappedBy = "project")
    private List<Transaction> transactions;

    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<ProjectUserRelation> projectUserRelations;

    @ManyToMany(mappedBy = "projects", fetch = FetchType.EAGER)
    private List<CompanyRelationship> companyRelationships;

    @OneToMany
    private  Set<Document> documents;
}
