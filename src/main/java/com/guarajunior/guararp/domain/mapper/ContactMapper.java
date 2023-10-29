package com.guarajunior.guararp.domain.mapper;

import com.guarajunior.guararp.infra.model.Contact;
import com.guarajunior.guararp.api.dto.contact.request.ContactCreateRequest;
import com.guarajunior.guararp.api.dto.contact.response.ContactResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ContactMapper {
	 private final ModelMapper modelMapper;

	    public ContactMapper(ModelMapper modelMapper) {
	        this.modelMapper = modelMapper;
	    }

	    public Contact toEntity(ContactCreateRequest contactCreateRequest) {
	        return modelMapper.map(contactCreateRequest, Contact.class);
	    }

	    public ContactCreateRequest toDTO(Contact contact) {
	        return modelMapper.map(contact, ContactCreateRequest.class);
	    }
	    
	    public ContactResponse toResponseDTO(Contact contact) {
	    	return modelMapper.map(contact, ContactResponse.class);
	    }
}
