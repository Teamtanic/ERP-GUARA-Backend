package com.guarajunior.rp.model;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Data
@Entity
public class UserPrivilege {
	@Id
	@GeneratedValue(generator = "UUID")
	private UUID id;
	private String name;
	private String description;
	
	@ManyToMany(mappedBy = "privileges")
	@JsonBackReference
	private List<Role> roles;
	
	@ManyToMany(mappedBy = "privileges")
	@JsonBackReference
	private List<Department> departments;
}
