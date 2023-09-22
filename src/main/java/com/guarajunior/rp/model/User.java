package com.guarajunior.rp.model;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
public class User implements UserDetails {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "UUID")
	private UUID id;
	
	private String name;
	private String login;
	private String password;
	private String prontuary;
	private Boolean active;
	private Boolean status;
	
	@ManyToOne
	@JoinColumn(name = "id_department")
	private Department department;
	
	@ManyToOne
	@JoinColumn(name = "id_role")
	private Role role;
	
	private UUID createdBy;
	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date createdAt;
	private UUID updatedBy;
	@Column(name = "updated_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;
	
	@OneToMany(mappedBy = "user")
    private List<ProjectUserRelation> projectUserRelations;
	
	@ManyToOne
	@JoinColumn(name = "id_course")
	private Course course;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<>();

		if (role != null) {
			// Adiciona as autoridades com base nos privilegios relacionados à Role
		    List<UserPrivilege> rolePrivileges = role.getPrivileges();
		    for (UserPrivilege privilege : rolePrivileges) {
		        authorities.add(new SimpleGrantedAuthority(privilege.getName()));
		    }
		}

		if (department != null) {
		    // Adiciona as autoridades com base nos privilegios relacionados ao Department
		    List<UserPrivilege> departmentPrivileges = department.getPrivileges();
		    for (UserPrivilege privilege : departmentPrivileges) {
		        authorities.add(new SimpleGrantedAuthority(privilege.getName()));
		    }
		}

	    return authorities;
	}

	@Override
	public String getUsername() {
		return login;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}

