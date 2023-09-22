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
import com.guarajunior.rp.model.dto.project.ProjectDTO;
import com.guarajunior.rp.model.dto.project.ProjectResponseDTO;
import com.guarajunior.rp.service.ProjectService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/projetos")
public class ProjectController {
	@Autowired
	private ProjectService projectService;
	
	@GetMapping
	public ResponseEntity<?> list(@RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size){
		try {
			Page<ProjectResponseDTO> projects = projectService.getAllProjects(page, size);
			
			return ResponseEntity.status(HttpStatus.OK).body(projects);
		} catch(CompanyServiceException  e) {
			String errorMessage = "Erro ao listar projetos";
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, errorMessage));
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable UUID id) {
		try {			
			ProjectResponseDTO project = projectService.getProjectById(id);
			
			return ResponseEntity.status(HttpStatus.OK).body(project);
		} catch(CompanyServiceException  e) {
			String errorMessage = "Erro ao resgatar projeto";
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, errorMessage));
		}
	}
	
	@PatchMapping("/{id}")
	@Transactional
	public ResponseEntity<?> update(@Valid @PathVariable UUID id, @RequestBody Map<String, Object> fields) {
		try {
			ProjectResponseDTO updatedProject = projectService.updateProject(id, fields);
			
			return ResponseEntity.status(HttpStatus.OK).body(updatedProject);
		} catch(CompanyServiceException  e) {
			String errorMessage = "Erro ao atualizar projetos";
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, errorMessage));
		}
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> register(@Valid @RequestBody ProjectDTO projectDTO){
		try {
			ProjectResponseDTO createdProject = projectService.createProject(projectDTO);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(createdProject);
		} catch(CompanyServiceException  e) {
			String errorMessage = "Erro ao criar projeto";
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, errorMessage));
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable UUID id){
		try {
			projectService.deactivateProject(id);
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch(CompanyServiceException  e) {
			String errorMessage = "Erro ao deletar projeto";
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, errorMessage));
		}
	}
}
