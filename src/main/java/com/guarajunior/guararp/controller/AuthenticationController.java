package com.guarajunior.guararp.controller;

import com.guarajunior.guararp.exception.CompanyServiceException;
import com.guarajunior.guararp.exception.InvalidTokenException;
import com.guarajunior.guararp.model.ErrorResponse;
import com.guarajunior.guararp.model.User;
import com.guarajunior.guararp.model.dto.user.*;
import com.guarajunior.guararp.repository.UserRepository;
import com.guarajunior.guararp.service.TokenService;
import com.guarajunior.guararp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private TokenService tokenService;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody @Valid AuthenticationDTO authenticationDTO) {
		var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationDTO.getLogin(), authenticationDTO.getPassword());
		try {
			var auth = this.authenticationManager.authenticate(usernamePassword);
			var token = tokenService.generateToken((User) auth.getPrincipal());
			
			return ResponseEntity.status(HttpStatus.OK).body(new LoginResponseDTO(token));
		} catch (CompanyServiceException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(401, e.getMessage()));
		}
	}
	
	@PostMapping("/registro")
	public ResponseEntity<?> register(@RequestParam("token") String token, @RequestBody @Valid RegisterDTO registerDTO){
		try {
			
			if (token == null || token.isEmpty()) {
				if(userRepository.findByLogin(registerDTO.getLogin()) != null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();			 
				UserResponseDTO user = userService.createUser(registerDTO);
				return ResponseEntity.status(HttpStatus.OK).body(user);	
			} else {
				if(userRepository.findByLogin(registerDTO.getLogin()) != null)  return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
				UserResponseDTO user = userService.userCreateNewAccount(registerDTO, token);
				return ResponseEntity.status(HttpStatus.OK).body(user);	
			}
			
		} catch (Exception e) {
			String errorMessage = "Erro ao recuperar criar usuario";
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, errorMessage));
		}
	}
	
	@PostMapping("/recuperar-senha")
	public ResponseEntity<?> resetPassword(@RequestParam("token") String token, @RequestBody UserEmailDTO userDTO, HttpServletRequest request) {
		try {
			userService.resetPasswordUserRequest(userDTO, request);
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch(CompanyServiceException  e) {
			String errorMessage = "Erro ao recuperar senha de usuario";
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, errorMessage));
		}
	}
	
	@PostMapping("/reset-password")
	 public ResponseEntity<?> resetPasswordWithToken(@RequestParam("token") String token, @RequestBody UserPasswordDTO passwordPostDTO){
		 try {
	            userService.resetPasswordWithToken(token, passwordPostDTO);
	            
	            return ResponseEntity.status(HttpStatus.OK).body("Redefinição de senha bem-sucedida");
	        } catch (InvalidTokenException e) {
	            String errorMessage = "Token inválido ou expirado";
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(400, errorMessage));
	        } catch (Exception e) {
	            String errorMessage = "Erro ao redefinir a senha";
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, errorMessage));
	        }
	 }
}
