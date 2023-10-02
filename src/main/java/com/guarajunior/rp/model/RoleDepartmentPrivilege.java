package com.guarajunior.rp.model;

import java.io.Serializable;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "role_department_has_privilege")
public class RoleDepartmentPrivilege {
	@EmbeddedId
    private RoleDepartmentPrivilegeKey id;

    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "id_role")
    @JsonIgnore
    private Role role;

    @ManyToOne
    @MapsId("departmentId")
    @JoinColumn(name = "id_department")
    @JsonIgnore
    private Department department;
    
    @ManyToOne
    @MapsId("userPrivilegeId")
    @JoinColumn(name = "id_user_privilege")
    @JsonIgnore
    private UserPrivilege userPrivilege;
	
    @Data
    @Embeddable
    public static class RoleDepartmentPrivilegeKey implements Serializable {
		private static final long serialVersionUID = 1L;

		@Column(name = "id_role")
        private UUID roleId;

        @Column(name = "id_department")
        private UUID departmentId;
        
        @Column(name = "id_user_privilege")
        private UUID userPrivilegeId;
    }
}
