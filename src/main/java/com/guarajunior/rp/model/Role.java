package com.guarajunior.rp.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Role {
	@Id
	@GeneratedValue(generator = "UUID")
	private UUID id;
	private String name;
	
	@OneToMany(mappedBy = "role")
	@JsonIgnore
	private List<User> users;
	
	@OneToMany(mappedBy = "role")
	@JsonIgnore
	private List<RoleDepartmentPrivilege> privileges; 
}
