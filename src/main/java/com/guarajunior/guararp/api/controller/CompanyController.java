package com.guarajunior.guararp.api.controller;

import com.guarajunior.guararp.api.dto.company.request.CompanyCreateRequest;
import com.guarajunior.guararp.api.dto.company.response.CompanyResponse;
import com.guarajunior.guararp.api.dto.productwarehouse.response.ProductWarehouseResponse;
import com.guarajunior.guararp.api.dto.project.response.ProjectResponse;
import com.guarajunior.guararp.domain.service.CompanyService;
import com.guarajunior.guararp.domain.service.ProjectService;
import com.guarajunior.guararp.domain.service.WarehouseService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/empresas")
public class CompanyController {
    private final CompanyService companyService;
    private final ProjectService projectService;
    private final WarehouseService warehouseService;

    public CompanyController(CompanyService companyService, ProjectService projectService, WarehouseService warehouseService) {
        this.companyService = companyService;
        this.projectService = projectService;
        this.warehouseService = warehouseService;
    }

    @GetMapping
    public ResponseEntity<Page<CompanyResponse>> list(@RequestParam(required = false) String relationship, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.getAllCompanies(page, size, relationship));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.getCompanyById(id));
    }

    @PatchMapping("/{id}")
    @Transactional
    public ResponseEntity<CompanyResponse> update(@Valid @PathVariable UUID id, @RequestBody Map<String, Object> fields) {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.updateCompany(id, fields));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<CompanyResponse> register(@Valid @RequestBody CompanyCreateRequest companyCreateRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(companyService.createCompany(companyCreateRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCompanyRelationships(@PathVariable UUID id) {
        companyService.deactivateCompanyRelationships(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/desativa-relacao/{idRelacao}")
    public ResponseEntity<?> deleteCompanyRelationship(@PathVariable UUID idRelacao) {
        companyService.deactivateCompanyRelationship(idRelacao);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{id}/projetos")
    public  ResponseEntity<Page<ProjectResponse>> listCompanyProjects(@PathVariable UUID id, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        return ResponseEntity.status(HttpStatus.OK).body(projectService.getAllCompanyProjects(id, page, size));
    }
    @GetMapping("/{id}/produtos")
    public  ResponseEntity<Page<ProductWarehouseResponse>> listCompanyProducts(@PathVariable UUID id, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        return ResponseEntity.status(HttpStatus.OK).body(warehouseService.getAllItemsByCompany(id, page, size));
    }
}
