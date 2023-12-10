package com.guarajunior.guararp.domain.service;

import com.guarajunior.guararp.api.dto.role.request.RoleCreateRequest;
import com.guarajunior.guararp.api.dto.role.request.RolePermissionRequest;
import com.guarajunior.guararp.api.dto.role.request.RoleUpdateRequest;
import com.guarajunior.guararp.api.dto.role.response.RolePermissionResponse;
import com.guarajunior.guararp.api.dto.role.response.RoleResponse;
import com.guarajunior.guararp.infra.model.Department;
import com.guarajunior.guararp.infra.model.Role;
import com.guarajunior.guararp.infra.model.RolePermission;
import com.guarajunior.guararp.infra.repository.RolePermissionRepository;
import com.guarajunior.guararp.infra.repository.RoleRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    private final ModelMapper mapper;
    private final EntityManager entityManager;
    private final RolePermissionRepository rolePermissionRepository;

    public RoleService(RoleRepository roleRepository, ModelMapper mapper, EntityManager entityManager, RolePermissionRepository rolePermissionRepository) {
        this.roleRepository = roleRepository;
        this.mapper = mapper;
        this.entityManager = entityManager;
        this.rolePermissionRepository = rolePermissionRepository;
    }

    public Page<RoleResponse> getAllRoles(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Role> rolePage = roleRepository.findAll(pageable);

        return new PageImpl<>(rolePage.getContent().stream().map(role -> mapper.map(role, RoleResponse.class)).toList(), rolePage.getPageable(), rolePage.getTotalElements());
    }

    public RoleResponse createRole(RoleCreateRequest roleCreateRequest) {
        Role roleToCreate = mapper.map(roleCreateRequest, Role.class);

        List<RolePermission> rolePermissions = roleCreateRequest.getRolePermissions().stream().map(rp -> {
            Department department = entityManager.getReference(Department.class, rp.getDepartmentId());
            return new RolePermission(null, roleToCreate, department, rp.getPermissions().stream().map(Enum::name).toList());
        }).toList();

        roleToCreate.setRolePermissions(rolePermissions);

        return mapper.map(roleRepository.save(roleToCreate), RoleResponse.class);
    }

    public RoleResponse getRoleById(UUID id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cargo não encontrado"));
        return mapper.map(role, RoleResponse.class);
    }

    public RoleResponse updateRole(UUID id, RoleUpdateRequest roleUpdateRequest) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cargo não encontrado"));
        mapper.map(roleUpdateRequest, role);

        return mapper.map(roleRepository.save(role), RoleResponse.class);
    }

    public void deleteRole(UUID id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new RuntimeException("Cargo não encontrado"));
        roleRepository.delete(role);
    }

    public RolePermissionResponse addRolePermission(UUID roleId, RolePermissionRequest rolePermissionRequest) {
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new RuntimeException("Cargo não encontrado"));
        Department department = entityManager.getReference(Department.class, rolePermissionRequest.getDepartmentId());

        RolePermission rolePermission = mapper.map(rolePermissionRequest, RolePermission.class);
        rolePermission.setRole(role);
        rolePermission.setDepartment(department);

        return mapper.map(rolePermissionRepository.save(rolePermission), RolePermissionResponse.class);
    }

    public RolePermissionResponse editRolePermission(UUID id, RolePermissionRequest rolePermissionRequest) {
        RolePermission rolePermission = rolePermissionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("RolePermission não encontrado"));
        mapper.map(rolePermissionRequest, rolePermission);

        return mapper.map(rolePermissionRepository.save(rolePermission), RolePermissionResponse.class);
    }

    public void removeRolePermission(UUID id) {
        RolePermission rolePermission = rolePermissionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("RolePermission não encontrado"));
        rolePermissionRepository.delete(rolePermission);
    }
}
