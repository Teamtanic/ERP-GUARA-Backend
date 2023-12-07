package com.guarajunior.guararp.api.controller;

import com.guarajunior.guararp.api.dto.project.request.ProjectCreateRequest;
import com.guarajunior.guararp.api.dto.project.request.ProjectOfferingsUpdateRequest;
import com.guarajunior.guararp.api.dto.project.request.ProjectUpdateRequest;
import com.guarajunior.guararp.api.dto.project.response.ProjectResponse;
import com.guarajunior.guararp.domain.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/projetos")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public ResponseEntity<Page<ProjectResponse>> list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "true") Boolean active) {
        return ResponseEntity.status(HttpStatus.OK).body(projectService.getAllProjects(active, page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(projectService.getProjectById(id));
    }

    @PatchMapping("/{id}")
    @Transactional
    public ResponseEntity<ProjectResponse> update(@Valid @PathVariable UUID id, @RequestBody ProjectUpdateRequest projectUpdateRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(projectService.updateProject(id, projectUpdateRequest));
    }

    @PatchMapping("/{id}/ofertas/adicionar")
    @Transactional
    public ResponseEntity<ProjectResponse> addOfferings(@Valid @PathVariable UUID id, @RequestBody ProjectOfferingsUpdateRequest dto) {
        return ResponseEntity.status(HttpStatus.OK).body(projectService.addProjectOfferings(id, dto.offeringIds()));
    }

    @PatchMapping("/{id}/ofertas/remover")
    @Transactional
    public ResponseEntity<ProjectResponse> removeOfferings(@Valid @PathVariable UUID id, @RequestBody ProjectOfferingsUpdateRequest dto) {
        return ResponseEntity.status(HttpStatus.OK).body(projectService.removeProjectOfferings(id, dto.offeringIds()));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ProjectResponse> register(@Valid @RequestBody ProjectCreateRequest projectCreateRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.createProject(projectCreateRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        projectService.deactivateProject(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
