package com.guarajunior.rp.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guarajunior.rp.exception.CompanyServiceException;
import com.guarajunior.rp.exception.InsufficientProductException;
import com.guarajunior.rp.model.ErrorResponse;
import com.guarajunior.rp.model.dto.transaction.TransactionDTO;
import com.guarajunior.rp.model.dto.transaction.TransactionResponseDTO;
import com.guarajunior.rp.service.TransactionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/transacoes")
public class TransactionController {
	@Autowired
	private TransactionService transactionService;
	
	@GetMapping
	public ResponseEntity<?> list(@RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "10") Integer size){
		try {
			Page<TransactionResponseDTO> transactions = transactionService.listAll(page, size);
			
			return ResponseEntity.status(HttpStatus.OK).body(transactions);
		} catch(Exception e) {
			String errorMessage = "Erro ao listar transações";
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, errorMessage));
		}
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> register(@Valid @RequestBody TransactionDTO transactionDTO) {
		try {
			TransactionResponseDTO createdTransaction = transactionService.createTransaction(transactionDTO);

			return ResponseEntity.status(HttpStatus.CREATED).body(createdTransaction);
		} catch (CompanyServiceException e) {
			String errorMessage = "Erro ao salvar empresa";
			// String errorLogMessage = "Erro ao salvar empresa: " + e.getMessage()";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, errorMessage));
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable UUID id) {
		try {
			TransactionResponseDTO transaction = transactionService.getTransactionById(id);
			
			return ResponseEntity.status(HttpStatus.OK).body(transaction);
		} catch (Exception e) {
			String errorMessage = "Erro ao resgatar dados de transação";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, errorMessage));
		}
	}
	
}
