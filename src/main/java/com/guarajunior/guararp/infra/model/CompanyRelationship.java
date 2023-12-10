package com.guarajunior.guararp.infra.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.guarajunior.guararp.infra.enums.BusinessRelationshipType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity
public class CompanyRelationship {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID idCompanyRelationship;

    @ManyToOne
    @JoinColumn(name = "id_company", nullable = false, referencedColumnName = "id")
    @JsonIgnore
    private Company company;

    @Enumerated(EnumType.STRING)
    private BusinessRelationshipType businessRelationship;

    private Boolean active = true;

    @OneToMany(mappedBy = "companyRelationship", fetch = FetchType.EAGER)
    private List<SupplierProduct> supplierProducts;

    @ManyToMany
    @JoinTable(
            name = "company_relationship_has_project",
            joinColumns = @JoinColumn(name = "id_company_relationship"),
            inverseJoinColumns = @JoinColumn(name = "id_project")
    )
    @JsonIgnore
    private List<Project> projects;
}
