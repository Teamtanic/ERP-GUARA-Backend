package com.guarajunior.guararp.infra.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolePermission {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @ManyToOne
    @JsonIgnore
    private Role role;

    @ManyToOne
    @JsonIgnore
    private Department department;

    @ElementCollection
    private List<String> permissions;

    public UUID getDepartmentId() {
        return department.getId();
    }

    public UUID getRoleId() {
        return role.getId();
    }
}