package com.guarajunior.guararp.infra.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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

    @OneToMany
    @JsonIgnore
    private List<User> users;

    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
    private List<RolePermission> rolePermissions;
}
