package com.guarajunior.guararp.api.controller;

import com.guarajunior.guararp.api.dto.company.request.CompanyCreateRequest;
import com.guarajunior.guararp.domain.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public ResponseEntity<?> list(@RequestParam(required = false) String relationship, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.getAllCompanies(page, size, relationship));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.getCompanyById(id));
    }

    @PatchMapping("/{id}")
    @Transactional
    public ResponseEntity<?> update(@Valid @PathVariable UUID id, @RequestBody Map<String, Object> fields) {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.updateCompany(id, fields));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> register(@Valid @RequestBody CompanyCreateRequest companyCreateRequest) {
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
}
