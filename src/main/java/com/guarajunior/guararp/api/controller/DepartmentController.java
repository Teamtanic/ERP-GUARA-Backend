package com.guarajunior.guararp.api.controller;

import com.guarajunior.guararp.api.dto.department.request.DepartmentCreateRequest;
import com.guarajunior.guararp.api.dto.department.response.DepartmentResponse;
import com.guarajunior.guararp.domain.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/departamentos")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public ResponseEntity<Page<DepartmentResponse>> list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        return ResponseEntity.status(HttpStatus.OK).body(departmentService.getAllDepartments(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(departmentService.getDepartmentById(id));
    }
    
    @GetMapping("/pesquisa")
    public ResponseEntity<Page<DepartmentResponse>> searchDepartments(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "3") Integer size) {
        return ResponseEntity.status(HttpStatus.OK).body(departmentService.searchDepartmentsByName(name, page, size));
    }

    @PatchMapping("/{id}")
    @Transactional
    public ResponseEntity<DepartmentResponse> update(@Valid @PathVariable UUID id, @RequestBody DepartmentCreateRequest departmentCreateRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(departmentService.updateDepartment(id, departmentCreateRequest));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DepartmentResponse> register(@Valid @RequestBody DepartmentCreateRequest departmentCreateRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(departmentService.createDepartment(departmentCreateRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        departmentService.deactivateDepartment(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
