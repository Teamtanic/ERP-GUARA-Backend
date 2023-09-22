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

import com.guarajunior.rp.model.ErrorResponse;
import com.guarajunior.rp.model.dto.bankaccount.BankAccountDTO;
import com.guarajunior.rp.model.dto.bankaccount.BankAccountResponseDTO;
import com.guarajunior.rp.service.BankAccountService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/bancos")
public class BankAccountController {
	@Autowired
	private BankAccountService bankAccountService;
	
	@GetMapping
	public ResponseEntity<?> list(@RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size){
		try {
			Page<BankAccountResponseDTO> bankAccounts = bankAccountService.listAll(page, size);
			
			return ResponseEntity.status(HttpStatus.OK).body(bankAccounts);
		} catch (Exception e) {
			String errorMessage = "Erro ao listar contas de bancos";
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, errorMessage));
		}
	}
	
	@PostMapping
	public ResponseEntity<?> register(@Valid @RequestBody BankAccountDTO bankAccountDTO){
		try {
			BankAccountResponseDTO createdBankAccount = bankAccountService.createAccount(bankAccountDTO);
			
			return ResponseEntity.status(HttpStatus.OK).body(createdBankAccount);
		} catch (Exception e) {
			String errorMessage = "Erro ao criar conta de banco";
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, errorMessage));
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable UUID id){
		try {
			BankAccountResponseDTO bankAccounts = bankAccountService.getBankAccountById(id);
			
			return ResponseEntity.status(HttpStatus.OK).body(bankAccounts);
		} catch (Exception e) {
			String errorMessage = "Erro ao listar contas de bancos";
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, errorMessage));
		}
	}
	
	@PatchMapping("/{id}")
	@Transactional
	public ResponseEntity<?> update(@Valid @PathVariable UUID id, @RequestBody Map<String, Object> fields) {
		try {
			BankAccountResponseDTO updatedBankAccount = bankAccountService.updateBankAccount(id, fields);
			
			return ResponseEntity.status(HttpStatus.OK).body(updatedBankAccount);
		} catch (Exception e) {
			String errorMessage = "Erro ao editar conta de banco";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, errorMessage));
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable UUID id) {
		try {
			bankAccountService.deactivateBankAccount(id);
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (Exception e) {
			String errorMessage = "Erro ao excluir conta de banco";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, errorMessage));
		}
	}
}
