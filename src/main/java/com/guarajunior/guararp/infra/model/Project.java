package com.guarajunior.guararp.infra.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;
import java.util.UUID;

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
