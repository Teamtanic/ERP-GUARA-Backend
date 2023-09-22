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
import com.guarajunior.rp.model.dto.offering.OfferingDTO;
import com.guarajunior.rp.model.dto.offering.OfferingResponseDTO;
import com.guarajunior.rp.service.OfferingService;

import jakarta.validation.Valid;

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
			Page<OfferingResponseDTO> offerings = null;
			
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
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, errorMessage));
		}
	}
	
	@GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id){
		try {
			OfferingResponseDTO offering = offeringService.getOfferingById(id);
			
			return ResponseEntity.status(HttpStatus.OK).body(offering);
		} catch (Exception e) {
			String errorMessage = "Erro ao resgatar oferta";
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, errorMessage));
		}
	}
	
	@PatchMapping("/{id}")
	@Transactional
	public ResponseEntity<?> update(@Valid @PathVariable UUID id, @RequestBody Map<String, Object> fields) {
		try {
			OfferingResponseDTO offering = offeringService.updateOffering(id, fields);
			
			return ResponseEntity.status(HttpStatus.OK).body(offering);
		} catch (Exception e) {
			String errorMessage = "Erro ao atualizar oferta";
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, errorMessage));
		}
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> register(@Valid @RequestBody OfferingDTO offeringDTO) {
		try {
			OfferingResponseDTO createdOffering = offeringService.createOffering(offeringDTO);
			
			return ResponseEntity.status(HttpStatus.OK).body(createdOffering);
		} catch (Exception e) {
			String errorMessage = "Erro ao criar oferta";
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, errorMessage));
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable UUID id){
		try {
			offeringService.deactivateOffering(id);
			
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (Exception e) {
			String errorMessage = "Erro ao deletar oferta";
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, errorMessage));
		}
	}
}
