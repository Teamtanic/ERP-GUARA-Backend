package com.guarajunior.guararp.api.controller;

import com.guarajunior.guararp.api.error.exception.CompanyServiceException;
import com.guarajunior.guararp.api.error.ErrorResponse;
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
	public ResponseEntity<?> list(@RequestParam(required = false) String relacao,
			@RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size){
		try {
			Page<CompanyResponse> companies = null;
			if (relacao != null && relacao.equals("clientes")) {
				companies = companyService.getAllCustomers(page, size);	
			} 
			else if (relacao != null && relacao.equals("fornecedores")) {
				companies = companyService.getAllSuppliers(page, size);
			} 
			else {				
				companies = companyService.getAllCompanies(page, size);	
			}

			return ResponseEntity.status(HttpStatus.OK).body(companies);		
		} catch(CompanyServiceException  e) {
			String errorMessage = "Erro ao listar clientes";
			//String errorLogMessage = errorMessage + ": " + e.getMessage();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message(errorMessage).build());
		}
	}
	
	@GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
		try {			
			CompanyResponse customer = companyService.getCompanyById(id);
			
		    return ResponseEntity.status(HttpStatus.OK).body(customer);
		} catch(CompanyServiceException e) {
			String errorMessage = "Erro ao resgatar dados do cliente";
			//String errorLogMessage = errorMessage + ": " + e.getMessage();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message(errorMessage).build());
		}
    }
	
	@PatchMapping("/{id}")
	@Transactional
	public ResponseEntity<?> update(@Valid @PathVariable UUID id, @RequestBody Map<String, Object> fields) {
		try {
			//Edita empresa
			CompanyResponse updatedCompany = companyService.updateCompany(id, fields);

			return ResponseEntity.status(HttpStatus.OK).body(updatedCompany);
		} catch (CompanyServiceException e) {
			String errorMessage = "Erro ao salvar empresa";
			// String errorLogMessage = "Erro ao salvar empresa: " + e.getMessage()";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message(errorMessage).build());
		}
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> register(@Valid @RequestBody CompanyCreateRequest companyCreateRequest) {
		try {
			//Cria empresa
			CompanyResponse createdCompany = companyService.createCompany(companyCreateRequest);

			return ResponseEntity.status(HttpStatus.CREATED).body(createdCompany);
		} catch (CompanyServiceException e) {
			String errorMessage = "Erro ao salvar empresa";
			// String errorLogMessage = "Erro ao salvar empresa: " + e.getMessage()";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message(errorMessage).build());
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCompanyRelationships(@PathVariable UUID id) {
		try {
			companyService.deactivateCompanyRelationships(id);
		    return ResponseEntity.status(HttpStatus.OK).build();
		} catch (CompanyServiceException e) {
			String errorMessage = "Erro ao deletar cliente";
			// String errorLogMessage = "Erro ao salvar empresa: " + e.getMessage()";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message(errorMessage).build());
		}
	}
	
	@DeleteMapping("/desativa-relacao/{idRelacao}")
	public ResponseEntity<?> deleteCompanyRelationship(@PathVariable UUID idRelacao) {
		try {
			companyService.deactivateCompanyRelationship(idRelacao);
		    return ResponseEntity.status(HttpStatus.OK).build();
		} catch (CompanyServiceException e) {
			String errorMessage = "Erro ao deletar cliente";
			// String errorLogMessage = "Erro ao salvar empresa: " + e.getMessage()";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message(errorMessage).build());
		}
	}
}
