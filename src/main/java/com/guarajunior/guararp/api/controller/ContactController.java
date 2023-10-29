package com.guarajunior.guararp.api.controller;

import com.guarajunior.guararp.infra.model.Contact;
import com.guarajunior.guararp.infra.model.ErrorResponse;
import com.guarajunior.guararp.infra.repository.ContactRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contato")
public class ContactController {
	@Autowired
	private ContactRepository contactRepository;
	
	@PostMapping
	public ResponseEntity<?> register(@Valid @RequestBody Contact contact) {
		try {
			Contact savedContact = contactRepository.save(contact);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(savedContact);
		} catch (Exception e) {
			String errorMessage = "Erro ao salvar contato";
			//String errorLogMessage = errorMessage + ": " + e.getMessage();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, errorMessage));
		}
	}
}
