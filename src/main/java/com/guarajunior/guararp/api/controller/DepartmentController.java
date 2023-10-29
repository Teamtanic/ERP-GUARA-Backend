package com.guarajunior.guararp.api.controller;

import com.guarajunior.guararp.api.error.exception.CompanyServiceException;
import com.guarajunior.guararp.infra.model.ErrorResponse;
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
	public ResponseEntity<?> list(@RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size){
		try {
			Page<DepartmentResponse> departments = departmentService.getAllDepartments(page, size);
			
			return ResponseEntity.status(HttpStatus.OK).body(departments);
		} catch(CompanyServiceException  e) {
			String errorMessage = "Erro ao listar departamentos";
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, errorMessage));
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable UUID id) {
		try {
			DepartmentResponse department = departmentService.getDepartmentById(id);
		
			return ResponseEntity.status(HttpStatus.OK).body(department);
		} catch(CompanyServiceException  e) {
			String errorMessage = "Erro ao resgatar departamento";
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, errorMessage));
		}
	}
	
	@PatchMapping("/{id}")
	@Transactional
	public ResponseEntity<?> update(@Valid @PathVariable UUID id, @RequestBody Map<String, Object> fields) {
		try {
			DepartmentResponse updatedDepartment = departmentService.updateDepartment(id, fields);
			
			return ResponseEntity.status(HttpStatus.OK).body(updatedDepartment);
		} catch(CompanyServiceException  e) {
			String errorMessage = "Erro ao atualizar departamento";
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, errorMessage));
		}
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> register(@Valid @RequestBody DepartmentCreateRequest departmentCreateRequest){
		try {
			DepartmentResponse createdDepartment = departmentService.createDepartment(departmentCreateRequest);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(createdDepartment);
		} catch(CompanyServiceException  e) {
			String errorMessage = "Erro ao criar departamento";
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, errorMessage));
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable UUID id){
		try {
			departmentService.deactivateDepartment(id);
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch(CompanyServiceException  e) {
			String errorMessage = "Erro ao deletar departamento";
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, errorMessage));
		}
	}
}
