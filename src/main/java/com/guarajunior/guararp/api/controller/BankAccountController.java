package com.guarajunior.guararp.api.controller;

import com.guarajunior.guararp.api.dto.bankaccount.request.BankAccountCreateRequest;
import com.guarajunior.guararp.domain.service.BankAccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/bancos")
public class BankAccountController {
    private final BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping
    public ResponseEntity<?> list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        return ResponseEntity.status(HttpStatus.OK).body(bankAccountService.listAll(page, size));
    }

    @PostMapping
    public ResponseEntity<?> register(@Valid @RequestBody BankAccountCreateRequest bankAccountCreateRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(bankAccountService.createAccount(bankAccountCreateRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(bankAccountService.getBankAccountById(id));
    }

    @PatchMapping("/{id}")
    @Transactional
    public ResponseEntity<?> update(@Valid @PathVariable UUID id, @RequestBody Map<String, Object> fields) {
        return ResponseEntity.status(HttpStatus.OK).body(bankAccountService.updateBankAccount(id, fields));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        bankAccountService.deactivateBankAccount(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
