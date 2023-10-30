package com.guarajunior.guararp.api.controller;

import com.guarajunior.guararp.api.error.exception.CompanyServiceException;
import com.guarajunior.guararp.api.error.ErrorResponse;
import com.guarajunior.guararp.api.dto.project.request.ProjectCreateRequest;
import com.guarajunior.guararp.api.dto.project.response.ProjectResponse;
import com.guarajunior.guararp.domain.service.ProjectService;
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
@RequestMapping("/projetos")
public class ProjectController {
	@Autowired
	private ProjectService projectService;
	
	@GetMapping
	public ResponseEntity<?> list(@RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size){
		try {
			Page<ProjectResponse> projects = projectService.getAllProjects(page, size);
			
			return ResponseEntity.status(HttpStatus.OK).body(projects);
		} catch(CompanyServiceException  e) {
			String errorMessage = "Erro ao listar projetos";
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message(errorMessage).build());
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable UUID id) {
		try {			
			ProjectResponse project = projectService.getProjectById(id);
			
			return ResponseEntity.status(HttpStatus.OK).body(project);
		} catch(CompanyServiceException  e) {
			String errorMessage = "Erro ao resgatar projeto";
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message(errorMessage).build());
		}
	}
	
	@PatchMapping("/{id}")
	@Transactional
	public ResponseEntity<?> update(@Valid @PathVariable UUID id, @RequestBody Map<String, Object> fields) {
		try {
			ProjectResponse updatedProject = projectService.updateProject(id, fields);
			
			return ResponseEntity.status(HttpStatus.OK).body(updatedProject);
		} catch(CompanyServiceException  e) {
			String errorMessage = "Erro ao atualizar projetos";
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message(errorMessage).build());
		}
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> register(@Valid @RequestBody ProjectCreateRequest projectCreateRequest){
		try {
			ProjectResponse createdProject = projectService.createProject(projectCreateRequest);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(createdProject);
		} catch(CompanyServiceException  e) {
			String errorMessage = "Erro ao criar projeto";
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message(errorMessage).build());
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable UUID id){
		try {
			projectService.deactivateProject(id);
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch(CompanyServiceException  e) {
			String errorMessage = "Erro ao deletar projeto";
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message(errorMessage).build());
		}
	}
}
