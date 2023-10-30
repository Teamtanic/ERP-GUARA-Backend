package com.guarajunior.guararp.api.controller;

import com.guarajunior.guararp.api.error.ErrorResponse;
import com.guarajunior.guararp.api.dto.offering.request.OfferingCreateRequest;
import com.guarajunior.guararp.api.dto.offering.response.OfferingResponse;
import com.guarajunior.guararp.domain.service.OfferingService;
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
@RequestMapping("/ofertas")
public class OfferingController {
	@Autowired
	private OfferingService offeringService;
	
	@GetMapping
	public ResponseEntity<?> list(@RequestParam(required = false) String tipo,
			@RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size){
		try {
			Page<OfferingResponse> offerings = null;
			
			if(tipo != null && tipo.equals("servicos")) {
				offerings = offeringService.getAllServices(page, size);
			} else if (tipo != null && tipo.equals("produtos")) {
				offerings = offeringService.getAllProducts(page, size);
			} else {
				offerings = offeringService.getAllOfferings(page, size);
			}
			
			return ResponseEntity.status(HttpStatus.OK).body(offerings);
		} catch (Exception e) {
			String errorMessage = "Erro ao listar ofertas";
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message(errorMessage).build());
		}
	}
	
	@GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id){
		try {
			OfferingResponse offering = offeringService.getOfferingById(id);
			
			return ResponseEntity.status(HttpStatus.OK).body(offering);
		} catch (Exception e) {
			String errorMessage = "Erro ao resgatar oferta";
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message(errorMessage).build());
		}
	}
	
	@PatchMapping("/{id}")
	@Transactional
	public ResponseEntity<?> update(@Valid @PathVariable UUID id, @RequestBody Map<String, Object> fields) {
		try {
			OfferingResponse offering = offeringService.updateOffering(id, fields);
			
			return ResponseEntity.status(HttpStatus.OK).body(offering);
		} catch (Exception e) {
			String errorMessage = "Erro ao atualizar oferta";
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message(errorMessage).build());
		}
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> register(@Valid @RequestBody OfferingCreateRequest offeringCreateRequest) {
		try {
			OfferingResponse createdOffering = offeringService.createOffering(offeringCreateRequest);
			
			return ResponseEntity.status(HttpStatus.OK).body(createdOffering);
		} catch (Exception e) {
			String errorMessage = "Erro ao criar oferta";
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message(errorMessage).build());
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable UUID id){
		try {
			offeringService.deactivateOffering(id);
			
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (Exception e) {
			String errorMessage = "Erro ao deletar oferta";
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message(errorMessage).build());
		}
	}
}
