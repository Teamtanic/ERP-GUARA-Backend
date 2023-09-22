package com.guarajunior.rp.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
public class Project {
	@Id
	@GeneratedValue(generator = "UUID")
	private UUID id;
	private String description;
	private Boolean status;
	private Boolean active;
	
	@ManyToOne
	@JoinColumn(name = "id_offering", nullable = false, referencedColumnName = "id")
	private Offering offering;
	
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
	
	@OneToMany(mappedBy = "project")
    private List<ProjectUserRelation> projectUserRelations;
	
	@ManyToMany(mappedBy = "projects")
	private List<CompanyRelationship> companyRelationships;
}
