package com.guarajunior.guararp.api.controller;

import com.guarajunior.guararp.api.dto.offering.request.OfferingCreateRequest;
import com.guarajunior.guararp.api.dto.offering.response.OfferingResponse;
import com.guarajunior.guararp.domain.service.OfferingService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/ofertas")
public class OfferingController {
    private final OfferingService offeringService;

    public OfferingController(OfferingService offeringService) {
        this.offeringService = offeringService;
    }

    @GetMapping
    public ResponseEntity<Page<OfferingResponse>> list(@RequestParam(required = false) String type, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        return ResponseEntity.status(HttpStatus.OK).body(offeringService.getAllOfferings(page, size, type));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfferingResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(offeringService.getOfferingById(id));
    }

    @PatchMapping("/{id}")
    @Transactional
    public ResponseEntity<OfferingResponse> update(@Valid @PathVariable UUID id, @RequestBody OfferingCreateRequest offeringCreateRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(offeringService.updateOffering(id, offeringCreateRequest));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<OfferingResponse> register(@Valid @RequestBody OfferingCreateRequest offeringCreateRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(offeringService.createOffering(offeringCreateRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        offeringService.deactivateOffering(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
