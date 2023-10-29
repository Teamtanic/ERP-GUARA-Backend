package com.guarajunior.guararp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;
import java.util.UUID;


@Data
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Company {
	@Id
	@GeneratedValue(generator = "UUID")
	private UUID id;
	
	private String name;
	private String cpf;
	private String cnpj;
	
	private UUID createdBy;
	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date createdAt;
	private UUID updatedBy;
	@Column(name = "updated_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;
	
	@JsonBackReference
	@OneToMany(mappedBy = "company")
	private List<CompanyRelationship> companyRelationships;
	
	@JsonBackReference
	@OneToMany(mappedBy = "company")
	private List<Contact> contact;
}