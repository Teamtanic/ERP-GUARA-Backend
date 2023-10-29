package com.guarajunior.guararp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;
import java.util.UUID;

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
