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

import com.guarajunior.rp.exception.CompanyServiceException;
import com.guarajunior.rp.model.ErrorResponse;
import com.guarajunior.rp.model.dto.productwarehouse.ProductWarehouseDTO;
import com.guarajunior.rp.model.dto.productwarehouse.ProductWarehouseResponseDTO;
import com.guarajunior.rp.service.WarehouseService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
public class WarehouseController {
	@Autowired
	private WarehouseService warehouseService;
	
	@GetMapping
	public ResponseEntity<?> list(
			@RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size){
		try {
			Page<ProductWarehouseResponseDTO> items = warehouseService.getAllItems(page, size);
			
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(items);
		} catch (Exception e) {
			String errorMessage = "Erro ao listar estoque";
			
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ErrorResponse(500, errorMessage));
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable UUID id) {
		try {
			ProductWarehouseResponseDTO product = warehouseService.getProductById(id);
			
			return ResponseEntity.status(HttpStatus.OK).body(product);
		} catch(CompanyServiceException  e) {
			String errorMessage = "Erro ao resgatar produto";
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, errorMessage));
		}
	}
	
	@PostMapping
	public ResponseEntity<?> register(@Valid @RequestBody ProductWarehouseDTO productWarehouseDTO){
		try {
			ProductWarehouseResponseDTO createdProduct = warehouseService.createProduct(productWarehouseDTO);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
		} catch (CompanyServiceException e) {
			String errorMessage = "Erro ao criar produto";
			// String errorLogMessage = "Erro ao salvar empresa: " + e.getMessage()";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, errorMessage));
		}
	}
	
	@PatchMapping("/{id}")
	@Transactional
	public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody  Map<String, Object> fields){
		try {
			ProductWarehouseResponseDTO updatedProduct = warehouseService.updateProduct(id, fields);
			
			return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
		} catch (Exception e) {
			String errorMessage = "Erro ao atualizar produto";
			// String errorLogMessage = "Erro ao salvar empresa: " + e.getMessage()";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, errorMessage));
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable UUID id){
		try {
			warehouseService.deactivateProduct(id);
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch(CompanyServiceException  e) {
			String errorMessage = "Erro ao deletar cargo";
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, errorMessage));
		}
	}
}
