package com.guarajunior.guararp.service;

import com.guarajunior.guararp.exception.CompanyServiceException;
import com.guarajunior.guararp.mapper.ContactMapper;
import com.guarajunior.guararp.model.Contact;
import com.guarajunior.guararp.model.dto.contact.ContactDTO;
import com.guarajunior.guararp.repository.ContactRepository;
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
    public void createContact(ContactDTO contactCreateDTO) {
    	try {
	        Contact newContact = contactMapper.toEntity(contactCreateDTO);
	
	        contactRepository.save(newContact);
	        
    	} catch(Exception e) {
    		throw new CompanyServiceException("Erro ao criar empresa: " + e.getMessage());
    	}
    }
}
