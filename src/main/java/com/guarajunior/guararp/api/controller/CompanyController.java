package com.guarajunior.guararp.api.controller;

import com.guarajunior.guararp.api.dto.company.request.CompanyCreateRequest;
import com.guarajunior.guararp.api.dto.company.response.CompanyResponse;
import com.guarajunior.guararp.domain.service.CompanyService;
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
@RequestMapping("/empresas")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @GetMapping
    public ResponseEntity<?> list(@RequestParam(required = false) String relacao, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        Page<CompanyResponse> companies = null;
        if (relacao != null && relacao.equals("clientes")) {
            companies = companyService.getAllCustomers(page, size);
        } else if (relacao != null && relacao.equals("fornecedores")) {
            companies = companyService.getAllSuppliers(page, size);
        } else {
            companies = companyService.getAllCompanies(page, size);
        }

        return ResponseEntity.status(HttpStatus.OK).body(companies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        CompanyResponse customer = companyService.getCompanyById(id);

        return ResponseEntity.status(HttpStatus.OK).body(customer);
    }

    @PatchMapping("/{id}")
    @Transactional
    public ResponseEntity<?> update(@Valid @PathVariable UUID id, @RequestBody Map<String, Object> fields) {
        //Edita empresa
        CompanyResponse updatedCompany = companyService.updateCompany(id, fields);

        return ResponseEntity.status(HttpStatus.OK).body(updatedCompany);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> register(@Valid @RequestBody CompanyCreateRequest companyCreateRequest) {
        //Cria empresa
        CompanyResponse createdCompany = companyService.createCompany(companyCreateRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdCompany);
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
