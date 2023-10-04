package com.guarajunior.rp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guarajunior.rp.exception.CompanyServiceException;
import com.guarajunior.rp.mapper.ContactMapper;
import com.guarajunior.rp.model.Contact;
import com.guarajunior.rp.model.dto.contact.ContactDTO;
import com.guarajunior.rp.repository.ContactRepository;

@Service
public class ContactService {
	@Autowired
	private ContactRepository contactRepository;
	@Autowired
	private ContactMapper contactMapper;
	
	@Transactional
    public void createContact(ContactDTO contactCreateDTO) {
    	try {
	        Contact newContact = contactMapper.toEntity(contactCreateDTO);
	
	        contactRepository.save(newContact);
	        
    	} catch(Exception e) {
    		throw new CompanyServiceException("Erro ao criar empresa: " + e.getMessage());
    	}
    }
}
