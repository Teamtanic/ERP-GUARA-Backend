package com.guarajunior.guararp.api.controller;

import com.guarajunior.guararp.infra.model.ErrorResponse;
import com.guarajunior.guararp.api.dto.bankaccount.request.BankAccountCreateRequest;
import com.guarajunior.guararp.api.dto.bankaccount.response.BankAccountResponse;
import com.guarajunior.guararp.domain.service.BankAccountService;
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
@RequestMapping("/bancos")
public class BankAccountController {
	@Autowired
	private BankAccountService bankAccountService;
	
	@GetMapping
	public ResponseEntity<?> list(@RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size){
		try {
			Page<BankAccountResponse> bankAccounts = bankAccountService.listAll(page, size);
			
			return ResponseEntity.status(HttpStatus.OK).body(bankAccounts);
		} catch (Exception e) {
			String errorMessage = "Erro ao listar contas de bancos";
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, errorMessage));
		}
	}
	
	@PostMapping
	public ResponseEntity<?> register(@Valid @RequestBody BankAccountCreateRequest bankAccountCreateRequest){
		try {
			BankAccountResponse createdBankAccount = bankAccountService.createAccount(bankAccountCreateRequest);
			
			return ResponseEntity.status(HttpStatus.OK).body(createdBankAccount);
		} catch (Exception e) {
			String errorMessage = "Erro ao criar conta de banco";
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, errorMessage));
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable UUID id){
		try {
			BankAccountResponse bankAccounts = bankAccountService.getBankAccountById(id);
			
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
			BankAccountResponse updatedBankAccount = bankAccountService.updateBankAccount(id, fields);
			
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
