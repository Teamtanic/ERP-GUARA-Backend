package com.guarajunior.guararp.repository;

import com.guarajunior.guararp.model.Department;
import com.guarajunior.guararp.model.Role;
import com.guarajunior.guararp.model.RoleDepartmentPrivilege;
import com.guarajunior.guararp.model.UserPrivilege;
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
