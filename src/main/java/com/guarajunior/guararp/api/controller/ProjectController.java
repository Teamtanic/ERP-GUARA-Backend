package com.guarajunior.guararp.api.controller;

import com.guarajunior.guararp.api.dto.project.request.ProjectCreateRequest;
import com.guarajunior.guararp.domain.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/projetos")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public ResponseEntity<?> list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "true") Boolean active) {
        return ResponseEntity.status(HttpStatus.OK).body(projectService.getAllProjects(active, page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(projectService.getProjectById(id));
    }

    @PatchMapping("/{id}")
    @Transactional
    public ResponseEntity<?> update(@Valid @PathVariable UUID id, @RequestBody Map<String, Object> fields) {
        return ResponseEntity.status(HttpStatus.OK).body(projectService.updateProject(id, fields));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> register(@Valid @RequestBody ProjectCreateRequest projectCreateRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.createProject(projectCreateRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        projectService.deactivateProject(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
