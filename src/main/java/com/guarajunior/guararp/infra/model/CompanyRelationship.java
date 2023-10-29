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
	
	@JsonIgnore	
	@ManyToOne
	@JoinColumn(name = "id_company", nullable = false, referencedColumnName = "id")
	private Company company;
	
	@Enumerated(EnumType.STRING)
	private BusinessRelationshipType businessRelationship;
	
	private Boolean active = true;
	
	@OneToMany(mappedBy = "companyRelationship")
    private List<SupplierProduct> supplierProducts;
	
	@ManyToMany
	@JoinTable(
	    name = "company_relationship_has_project",
	    joinColumns = @JoinColumn(name = "id_company_relationship"),
	    inverseJoinColumns = @JoinColumn(name = "id_project")
	)
	private List<Project> projects;
}
