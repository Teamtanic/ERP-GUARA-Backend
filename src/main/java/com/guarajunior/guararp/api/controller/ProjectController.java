package com.guarajunior.guararp.api.controller;

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
    public ResponseEntity<?> list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        Page<ProjectResponse> projects = projectService.getAllProjects(page, size);

        return ResponseEntity.status(HttpStatus.OK).body(projects);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        ProjectResponse project = projectService.getProjectById(id);

        return ResponseEntity.status(HttpStatus.OK).body(project);
    }

    @PatchMapping("/{id}")
    @Transactional
    public ResponseEntity<?> update(@Valid @PathVariable UUID id, @RequestBody Map<String, Object> fields) {
        ProjectResponse updatedProject = projectService.updateProject(id, fields);

        return ResponseEntity.status(HttpStatus.OK).body(updatedProject);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> register(@Valid @RequestBody ProjectCreateRequest projectCreateRequest) {
        ProjectResponse createdProject = projectService.createProject(projectCreateRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdProject);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        projectService.deactivateProject(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
