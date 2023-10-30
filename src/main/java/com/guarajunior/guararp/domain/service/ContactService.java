package com.guarajunior.guararp.domain.service;

import com.guarajunior.guararp.api.dto.contact.request.ContactCreateRequest;
import com.guarajunior.guararp.domain.mapper.ContactMapper;
import com.guarajunior.guararp.infra.model.Contact;
import com.guarajunior.guararp.infra.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContactService {
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private ContactMapper contactMapper;

    @Transactional
    public void createContact(ContactCreateRequest contactCreateDTO) {
        Contact newContact = contactMapper.toEntity(contactCreateDTO);
        contactRepository.save(newContact);
    }
}
