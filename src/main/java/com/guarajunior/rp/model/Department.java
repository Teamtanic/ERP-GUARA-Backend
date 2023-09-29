package com.guarajunior.rp.model;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
	private List<User> users;

	@OneToMany(mappedBy = "department")
	private List<RoleDepartmentPrivilege> privileges; 
}
