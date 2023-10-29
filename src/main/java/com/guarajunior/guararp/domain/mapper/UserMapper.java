package com.guarajunior.guararp.domain.mapper;

import com.guarajunior.guararp.infra.model.User;
import com.guarajunior.guararp.api.dto.user.request.UserUpdateRequest;
import com.guarajunior.guararp.api.dto.user.response.UserResponse;
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
    
    public User toEntity(UserUpdateRequest userUpdateRequest) {
    	return modelMapper.map(userUpdateRequest, User.class);
    }
    
    public UserUpdateRequest toDTO(User user) {
    	return modelMapper.map(user, UserUpdateRequest.class);
    }
    
    public UserResponse toResponseDTO(User user) {
    	return modelMapper.map(user, UserResponse.class);
    }
    
    public Page<UserResponse> pageToResponsePageDTO(Page<User> entityPage){
    	List<UserResponse> entityList = entityPage
								    			.getContent()
												.stream()
												.map(this::toResponseDTO)
												.collect(Collectors.toList());
    
    	return new PageImpl<>(entityList, entityPage.getPageable(), entityPage.getTotalElements());
    }
}
