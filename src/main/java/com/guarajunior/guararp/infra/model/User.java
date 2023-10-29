package com.guarajunior.guararp.infra.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.UUID;

@Data
@Entity
public class User {
	@Id
	@GeneratedValue(generator = "UUID")
	private UUID id;
	
	private String name;
	private String login;
	private String password;
	private String prontuary;
	private Boolean active;
	private Boolean status;
	
	@ManyToOne
	@JoinColumn(name = "id_department")
	@JsonIgnore
	private Department department;
	
	@ManyToOne
	@JoinColumn(name = "id_role")
	@JsonIgnore
	private Role role;
	
	private UUID createdBy;
	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date createdAt;
	private UUID updatedBy;
	@Column(name = "updated_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;
	
	@ManyToOne
	@JoinColumn(name = "id_course")
	private Course course;
	
	@OneToOne(mappedBy = "user")
	private Contact contact;
}

