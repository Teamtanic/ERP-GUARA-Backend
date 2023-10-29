package com.guarajunior.guararp.infra.repository;

import com.guarajunior.guararp.infra.model.Role;
import com.guarajunior.guararp.infra.model.Department;
import com.guarajunior.guararp.infra.model.RoleDepartmentPrivilege;
import com.guarajunior.guararp.infra.model.UserPrivilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleDepartmentPrivilegeRepository extends JpaRepository<RoleDepartmentPrivilege, RoleDepartmentPrivilege.RoleDepartmentPrivilegeKey> {
	@Query("SELECT rdp.userPrivilege FROM RoleDepartmentPrivilege rdp " +
	           "WHERE rdp.role = :role AND rdp.department = :department")
	    List<UserPrivilege> findUserPrivilegesByRoleAndDepartment(
	        @Param("role") Role role,
	        @Param("department") Department department
	    );
}
