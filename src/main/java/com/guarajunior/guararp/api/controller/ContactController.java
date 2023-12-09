package com.guarajunior.guararp.api.controller;

import com.guarajunior.guararp.api.dto.contact.request.ContactCreateRequest;
import com.guarajunior.guararp.api.dto.contact.response.ContactResponse;
import com.guarajunior.guararp.domain.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/contato")
public class ContactController {
    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping
    public ResponseEntity<ContactResponse> register(@Valid @RequestBody ContactCreateRequest contact) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contactService.createContact(contact));
    }

    @PatchMapping("/{id}")
    @Transactional
    public ResponseEntity<ContactResponse> update(@Valid @PathVariable UUID id, @RequestBody @Valid ContactCreateRequest contact) {
        return ResponseEntity.status(HttpStatus.OK).body(contactService.updateContact(id, contact));
    }
}
