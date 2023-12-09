package com.guarajunior.guararp.domain.service;

import com.guarajunior.guararp.api.dto.contact.request.ContactCreateRequest;
import com.guarajunior.guararp.api.dto.contact.response.ContactResponse;
import com.guarajunior.guararp.api.error.exception.EntityNotFoundException;
import com.guarajunior.guararp.domain.mapper.ContactMapper;
import com.guarajunior.guararp.infra.model.Contact;
import com.guarajunior.guararp.infra.repository.ContactRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ContactService {
    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;
    private final ModelMapper mapper;

    public ContactService(ContactRepository contactRepository, ContactMapper contactMapper, ModelMapper mapper) {
        this.contactRepository = contactRepository;
        this.contactMapper = contactMapper;
        this.mapper = mapper;
    }

    public ContactResponse createContact(ContactCreateRequest contactCreateDTO) {
        Contact newContact = contactMapper.toEntity(contactCreateDTO);
        return contactMapper.toResponseDTO(contactRepository.save(newContact));
    }

    public ContactResponse updateContact(UUID id, ContactCreateRequest contactCreateRequest) {
        Contact contact = contactRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Contato não encontrado"));
        mapper.map(contactCreateRequest, contact);

        return contactMapper.toResponseDTO(contactRepository.save(contact));
    }
}
