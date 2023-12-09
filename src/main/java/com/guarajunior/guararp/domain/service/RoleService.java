package com.guarajunior.guararp.domain.service;

import com.guarajunior.guararp.api.dto.role.request.RoleCreateRequest;
import com.guarajunior.guararp.api.dto.role.response.RoleResponse;
import com.guarajunior.guararp.infra.model.Role;
import com.guarajunior.guararp.infra.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    private final ModelMapper mapper;

    public RoleService(RoleRepository roleRepository, ModelMapper mapper) {
        this.roleRepository = roleRepository;
        this.mapper = mapper;
    }

    public Page<RoleResponse> getAllRoles(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Role> rolePage = roleRepository.findAll(pageable);

        return rolePage.map(role -> mapper.map(rolePage, RoleResponse.class));
    }

    public RoleResponse createRole(RoleCreateRequest roleCreateRequest) {
        Role roleToCreate = mapper.map(roleCreateRequest, Role.class);
        return mapper.map(roleRepository.save(roleToCreate), RoleResponse.class);
    }

    public RoleResponse getRoleById(UUID id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cargo não encontrado"));
        return mapper.map(role, RoleResponse.class);
    }

    public RoleResponse updateRole(UUID id, RoleCreateRequest roleCreateRequest) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cargo não encontrado"));
        mapper.map(roleCreateRequest, role);

        return mapper.map(roleRepository.save(role), RoleResponse.class);
    }

    public void deleteRole(UUID id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new RuntimeException("Cargo não encontrado"));
        roleRepository.delete(role);
    }
}
