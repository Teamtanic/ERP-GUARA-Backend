package com.guarajunior.rp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.guarajunior.rp.model.Department;
import com.guarajunior.rp.model.Role;
import com.guarajunior.rp.model.RoleDepartmentPrivilege;
import com.guarajunior.rp.model.UserPrivilege;

public interface RoleDepartmentPrivilegeRepository extends JpaRepository<RoleDepartmentPrivilege, RoleDepartmentPrivilege.RoleDepartmentPrivilegeKey> {
	@Query("SELECT rdp FROM RoleDepartmentPrivilege rdp WHERE rdp.role = :role AND rdp.department = :department AND rdp.userPrivilege = :userPrivilege")
	List<RoleDepartmentPrivilege> findRoleDepartmentPrivileges(@Param("role") Role idRole, @Param("department") Department department);
}
