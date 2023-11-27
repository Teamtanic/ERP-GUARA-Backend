package com.guarajunior.guararp.api.controller;

import com.guarajunior.guararp.api.dto.documenttype.request.DocumentTypeRequest;
import com.guarajunior.guararp.domain.service.DocumentTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/tipo-documento")
@RequiredArgsConstructor
public class DocumentTypeController {
    private final DocumentTypeService documentTypeService;

    @GetMapping
    public ResponseEntity<?> list() {
        return ResponseEntity.status(HttpStatus.OK).body(documentTypeService.getAll());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> register(@Valid @RequestBody DocumentTypeRequest documentTypeRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(documentTypeService.create(documentTypeRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(documentTypeService.getById(id));
    }

    @PatchMapping("/{id}")
    @Transactional
    public ResponseEntity<?> update(@PathVariable UUID id, @Valid @RequestBody DocumentTypeRequest documentTypeRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(documentTypeService.update(id, documentTypeRequest));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        documentTypeService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
