package com.guarajunior.rp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guarajunior.rp.model.User;
import com.guarajunior.rp.model.dto.user.AuthenticationDTO;
import com.guarajunior.rp.model.dto.user.LoginResponseDTO;
import com.guarajunior.rp.model.dto.user.RegisterDTO;
import com.guarajunior.rp.model.dto.user.UserResponseDTO;
import com.guarajunior.rp.repository.UserRepository;
import com.guarajunior.rp.service.TokenService;
import com.guarajunior.rp.service.UserService;

import jakarta.validation.Valid;

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
		var auth = this.authenticationManager.authenticate(usernamePassword);
		
		var token = tokenService.generateToken((User) auth.getPrincipal());
		
		return ResponseEntity.status(HttpStatus.OK).body(new LoginResponseDTO(token));
	}
	
	@PostMapping("/registro")
	public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO registerDTO){
		if(userRepository.findByLogin(registerDTO.getLogin()) != null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		
		UserResponseDTO user = userService.createUser(registerDTO);
		
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}
}
