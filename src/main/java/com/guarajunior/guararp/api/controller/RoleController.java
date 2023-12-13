package com.guarajunior.guararp.api.controller;

import com.guarajunior.guararp.api.dto.role.request.RoleCreateRequest;
import com.guarajunior.guararp.api.dto.role.request.RolePermissionRequest;
import com.guarajunior.guararp.api.dto.role.request.RoleUpdateRequest;
import com.guarajunior.guararp.api.dto.role.response.RolePermissionResponse;
import com.guarajunior.guararp.api.dto.role.response.RoleResponse;
import com.guarajunior.guararp.domain.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/cargos")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<Page<RoleResponse>> list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        return ResponseEntity.status(HttpStatus.OK).body(roleService.getAllRoles(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(roleService.getRoleById(id));
    }
    
    @GetMapping("/pesquisa")
    public ResponseEntity<Page<RoleResponse>> searchRoles(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "3") Integer size) {
        return ResponseEntity.status(HttpStatus.OK).body(roleService.searchRolesByName(name, page, size));
    }

    @PatchMapping("/{id}")
    @Transactional
    public ResponseEntity<RoleResponse> update(@Valid @PathVariable UUID id, @RequestBody RoleUpdateRequest roleUpdateRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(roleService.updateRole(id, roleUpdateRequest));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<RoleResponse> register(@Valid @RequestBody RoleCreateRequest roleCreateRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.createRole(roleCreateRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        roleService.deleteRole(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/{roleId}/permissoes")
    @Transactional
    public ResponseEntity<RolePermissionResponse> createRolePermission(@Valid @PathVariable UUID roleId, @RequestBody RolePermissionRequest rolePermissionRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(roleService.addRolePermission(roleId, rolePermissionRequest));
    }

    @PatchMapping("/{roleId}/permissoes/{id}")
    @Transactional
    public ResponseEntity<RolePermissionResponse> editRolePermission(@Valid @PathVariable UUID roleId, @Valid @PathVariable UUID id, @RequestBody RolePermissionRequest rolePermissionRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(roleService.editRolePermission(id, rolePermissionRequest));
    }

    @PostMapping("/{roleId}/permissoes/{id}")
    @Transactional
    public ResponseEntity<?> removeRolePermission(@Valid @PathVariable UUID roleId, @Valid @PathVariable UUID id) {
        roleService.removeRolePermission(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
