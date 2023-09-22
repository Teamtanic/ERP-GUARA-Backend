package com.guarajunior.rp.controller;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guarajunior.rp.exception.CompanyServiceException;
import com.guarajunior.rp.model.ErrorResponse;
import com.guarajunior.rp.model.dto.department.DepartmentDTO;
import com.guarajunior.rp.model.dto.department.DepartmentResponseDTO;
import com.guarajunior.rp.service.DepartmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/departamentos")
public class DepartmentController {
	@Autowired
	private DepartmentService departmentService;
	
	@GetMapping
	public ResponseEntity<?> list(@RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size){
		try {
			Page<DepartmentResponseDTO> departments = departmentService.getAllDepartments(page, size);
			
			return ResponseEntity.status(HttpStatus.OK).body(departments);
		} catch(CompanyServiceException  e) {
			String errorMessage = "Erro ao listar departamentos";
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, errorMessage));
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable UUID id) {
		try {
			DepartmentResponseDTO department = departmentService.getDepartmentById(id);
		
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
			DepartmentResponseDTO updatedDepartment = departmentService.updateDepartment(id, fields);
			
			return ResponseEntity.status(HttpStatus.OK).body(updatedDepartment);
		} catch(CompanyServiceException  e) {
			String errorMessage = "Erro ao atualizar departamento";
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, errorMessage));
		}
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> register(@Valid @RequestBody DepartmentDTO departmentDTO){
		try {
			DepartmentResponseDTO createdDepartment = departmentService.createDepartment(departmentDTO);
			
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
