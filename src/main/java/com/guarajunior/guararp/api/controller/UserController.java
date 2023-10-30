package com.guarajunior.guararp.api.controller;

import com.guarajunior.guararp.api.error.exception.CompanyServiceException;
import com.guarajunior.guararp.api.error.ErrorResponse;
import com.guarajunior.guararp.api.dto.user.request.UserUpdateRequest;
import com.guarajunior.guararp.api.dto.user.request.UserEmailRequest;
import com.guarajunior.guararp.api.dto.user.response.UserResponse;
import com.guarajunior.guararp.domain.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/usuarios")
public class UserController {
	@Autowired
	private UserService userService;
	
	@GetMapping
	public ResponseEntity<?> list(@RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size){
		try {
			Page<UserResponse> users = userService.getAllUsers(page, size);
			
			return ResponseEntity.status(HttpStatus.OK).body(users);
		} catch(CompanyServiceException  e) {
			String errorMessage = "Erro ao listar usuarios";
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message(errorMessage).build());
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable UUID id) {
		try {
			UserResponse user = userService.getUserById(id);
			
			return ResponseEntity.status(HttpStatus.OK).body(user);
		} catch(CompanyServiceException  e) {
			String errorMessage = "Erro ao resgatar usuario";
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message(errorMessage).build());
		}
	}
	
	@PatchMapping("/{id}")
	@Transactional
	public ResponseEntity<?> update(@Valid @PathVariable UUID id, @RequestBody UserUpdateRequest userUpdateRequest) {
		try {
			UserResponse updatedUser = userService.updateUser(id, userUpdateRequest);
		
			return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
		} catch(CompanyServiceException  e) {
			String errorMessage = "Erro ao atualizar usuario";
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message(errorMessage).build());
		}
	}
	
	@PostMapping("/token")
	@Transactional
	public ResponseEntity<?> createTokenForNewAccount(@RequestBody UserEmailRequest email, HttpServletRequest request){
		try {
			userService.userCreateNewAccountRequest(email, request);			
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch(CompanyServiceException e) {
			String errorMessage = "Erro ao gerar token para criação de usuário";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message(errorMessage).build());
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable UUID id){
		try {
			userService.deactivateUser(id);
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch(CompanyServiceException  e) {
			String errorMessage = "Erro ao deletar usuario";
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message(errorMessage).build());
		}
	}
	

	 
}
