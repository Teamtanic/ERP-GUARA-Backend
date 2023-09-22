package com.guarajunior.rp.model;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.guarajunior.rp.enums.BusinessRelationshipType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

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
