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
import com.guarajunior.rp.model.dto.company.CompanyCreateDTO;
import com.guarajunior.rp.model.dto.company.CompanyResponseDTO;
import com.guarajunior.rp.service.CompanyService;

import jakarta.validation.Valid;

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
			Page<CompanyResponseDTO> companies = null;
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
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, errorMessage));
		}
	}
	
	@GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
		try {			
			CompanyResponseDTO customer = companyService.getCompanyById(id);
			
		    return ResponseEntity.status(HttpStatus.OK).body(customer);
		} catch(CompanyServiceException e) {
			String errorMessage = "Erro ao resgatar dados do cliente";
			//String errorLogMessage = errorMessage + ": " + e.getMessage();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, errorMessage));
		}
    }
	
	@PatchMapping("/{id}")
	@Transactional
	public ResponseEntity<?> update(@Valid @PathVariable UUID id, @RequestBody Map<String, Object> fields) {
		try {
			//Edita empresa
			CompanyResponseDTO updatedCompany = companyService.updateCompany(id, fields);

			return ResponseEntity.status(HttpStatus.OK).body(updatedCompany);
		} catch (CompanyServiceException e) {
			String errorMessage = "Erro ao salvar empresa";
			// String errorLogMessage = "Erro ao salvar empresa: " + e.getMessage()";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, errorMessage));
		}
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> register(@Valid @RequestBody CompanyCreateDTO companyCreateDTO) {
		try {
			//Cria empresa
			CompanyResponseDTO createdCompany = companyService.createCompany(companyCreateDTO);

			return ResponseEntity.status(HttpStatus.CREATED).body(createdCompany);
		} catch (CompanyServiceException e) {
			String errorMessage = "Erro ao salvar empresa";
			// String errorLogMessage = "Erro ao salvar empresa: " + e.getMessage()";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, errorMessage));
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
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, errorMessage));
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
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, errorMessage));
		}
	}
}
