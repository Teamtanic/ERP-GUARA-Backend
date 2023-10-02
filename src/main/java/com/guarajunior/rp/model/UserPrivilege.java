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
public class UserPrivilege {
	@Id
	@GeneratedValue(generator = "UUID")
	private UUID id;
	private String name;
	private String description;
	
    @OneToMany(mappedBy = "userPrivilege")
    @JsonIgnore
    private List<RoleDepartmentPrivilege> privileges;
}