package com.guarajunior.guararp.mapper;

import com.guarajunior.guararp.model.Contact;
import com.guarajunior.guararp.model.dto.contact.ContactDTO;
import com.guarajunior.guararp.model.dto.contact.ContactResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

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
