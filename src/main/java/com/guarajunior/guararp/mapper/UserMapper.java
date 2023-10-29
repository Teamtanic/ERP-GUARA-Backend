package com.guarajunior.guararp.mapper;

import com.guarajunior.guararp.model.User;
import com.guarajunior.guararp.model.dto.user.UserDTO;
import com.guarajunior.guararp.model.dto.user.UserResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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
