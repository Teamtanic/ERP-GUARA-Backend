package com.guarajunior.rp.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import com.guarajunior.rp.model.User;
import com.guarajunior.rp.model.dto.user.UserDTO;
import com.guarajunior.rp.model.dto.user.UserResponseDTO;

@Component
public class UserMapper {
	private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
    	this.modelMapper = modelMapper;
    }
    
    public User toEntity(UserDTO userDTO) {
    	return modelMapper.map(userDTO, User.class);
    }
    
    public UserDTO toDTO(User user) {
    	return modelMapper.map(user, UserDTO.class);
    }
    
    public UserResponseDTO toResponseDTO(User user) {
    	return modelMapper.map(user, UserResponseDTO.class);
    }
    
    public Page<UserResponseDTO> pageToResponsePageDTO(Page<User> entityPage){
    	List<UserResponseDTO> entityList = entityPage
								    			.getContent()
												.stream()
												.map(this::toResponseDTO)
												.collect(Collectors.toList());
    
    	return new PageImpl<>(entityList, entityPage.getPageable(), entityPage.getTotalElements());
    }
}
