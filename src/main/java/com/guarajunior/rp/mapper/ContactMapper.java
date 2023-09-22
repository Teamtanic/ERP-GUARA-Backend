package com.guarajunior.rp.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.guarajunior.rp.model.Contact;
import com.guarajunior.rp.model.dto.contact.ContactDTO;
import com.guarajunior.rp.model.dto.contact.ContactResponseDTO;

@Component
public class ContactMapper {
	 private final ModelMapper modelMapper;

	    public ContactMapper(ModelMapper modelMapper) {
	        this.modelMapper = modelMapper;
	    }

	    public Contact toEntity(ContactDTO contactDTO) {
	        return modelMapper.map(contactDTO, Contact.class);
	    }

	    public ContactDTO toDTO(Contact contact) {
	        return modelMapper.map(contact, ContactDTO.class);
	    }
	    
	    public ContactResponseDTO toResponseDTO(Contact contact) {
	    	return modelMapper.map(contact, ContactResponseDTO.class);
	    }
}
