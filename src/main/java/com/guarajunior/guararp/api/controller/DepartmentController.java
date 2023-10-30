package com.guarajunior.guararp.api.controller;

import com.guarajunior.guararp.api.dto.department.request.DepartmentCreateRequest;
import com.guarajunior.guararp.api.dto.department.response.DepartmentResponse;
import com.guarajunior.guararp.domain.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/departamentos")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<?> list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        Page<DepartmentResponse> departments = departmentService.getAllDepartments(page, size);

        return ResponseEntity.status(HttpStatus.OK).body(departments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        DepartmentResponse department = departmentService.getDepartmentById(id);

        return ResponseEntity.status(HttpStatus.OK).body(department);
    }

    @PatchMapping("/{id}")
    @Transactional
    public ResponseEntity<?> update(@Valid @PathVariable UUID id, @RequestBody Map<String, Object> fields) {
        DepartmentResponse updatedDepartment = departmentService.updateDepartment(id, fields);

        return ResponseEntity.status(HttpStatus.OK).body(updatedDepartment);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> register(@Valid @RequestBody DepartmentCreateRequest departmentCreateRequest) {
        DepartmentResponse createdDepartment = departmentService.createDepartment(departmentCreateRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdDepartment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        departmentService.deactivateDepartment(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
