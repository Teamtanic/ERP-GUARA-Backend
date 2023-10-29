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