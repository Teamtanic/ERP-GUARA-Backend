package com.guarajunior.rp.model;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Department {
	@Id
	@GeneratedValue(generator = "UUID")
	private UUID id;
	
	private String name;
	
	@OneToMany(mappedBy = "department")
	@JsonIgnore
	private List<User> users;

	@OneToMany(mappedBy = "department")
	@JsonIgnore
	private List<RoleDepartmentPrivilege> privileges; 
}
