package com.guarajunior.guararp.api.controller;

import com.guarajunior.guararp.api.dto.productwarehouse.request.ProductWarehouseCreateRequest;
import com.guarajunior.guararp.domain.service.WarehouseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/produtos")
public class WarehouseController {
    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @GetMapping
    public ResponseEntity<?> list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        return ResponseEntity.status(HttpStatus.OK).body(warehouseService.getAllItems(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(warehouseService.getProductById(id));
    }

    @PostMapping
    public ResponseEntity<?> register(@Valid @RequestBody ProductWarehouseCreateRequest productWarehouseCreateRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(warehouseService.createProduct(productWarehouseCreateRequest));
    }

    @PatchMapping("/{id}")
    @Transactional
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody Map<String, Object> fields) {
        return ResponseEntity.status(HttpStatus.OK).body(warehouseService.updateProduct(id, fields));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        warehouseService.deactivateProduct(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
