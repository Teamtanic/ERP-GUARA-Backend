package com.guarajunior.guararp.api.controller;

import com.guarajunior.guararp.api.dto.transaction.request.TransactionCreateRequest;
import com.guarajunior.guararp.api.dto.transaction.response.TransactionResponse;
import com.guarajunior.guararp.api.error.ErrorResponse;
import com.guarajunior.guararp.domain.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/transacoes")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public ResponseEntity<?> list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        try {
            Page<TransactionResponse> transactions = transactionService.listAll(page, size);

            return ResponseEntity.status(HttpStatus.OK).body(transactions);
        } catch (Exception e) {
            String errorMessage = "Erro ao listar transações";

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message(errorMessage).build());
        }
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> register(@Valid @RequestBody TransactionCreateRequest transactionCreateRequest) {
        TransactionResponse createdTransaction = transactionService.createTransaction(transactionCreateRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdTransaction);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        try {
            TransactionResponse transaction = transactionService.getTransactionById(id);

            return ResponseEntity.status(HttpStatus.OK).body(transaction);
        } catch (Exception e) {
            String errorMessage = "Erro ao resgatar dados de transação";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message(errorMessage).build());
        }
    }

}
