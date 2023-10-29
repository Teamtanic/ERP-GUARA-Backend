package com.guarajunior.guararp.domain.mapper;

import com.guarajunior.guararp.infra.model.Department;
import com.guarajunior.guararp.api.dto.department.request.DepartmentCreateRequest;
import com.guarajunior.guararp.api.dto.department.response.DepartmentResponse;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DepartmentMapper {
	private final ModelMapper modelMapper;
	
	public DepartmentMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}
	
	public Department toEntity(DepartmentCreateRequest departamentDTO) {
		return modelMapper.map(departamentDTO, Department.class);
	}
	
	public DepartmentCreateRequest toDTO(Department departament) {
		return modelMapper.map(departament, DepartmentCreateRequest.class);
	}
	
	public DepartmentResponse toResponseDTO(Department departament) {
		return modelMapper.map(departament, DepartmentResponse.class);
	}
	
	public Page<DepartmentResponse> pageToResponsePageDTO(Page<Department> entityPage){
		List<DepartmentResponse> entityList = entityPage
													.getContent()
													.stream()
													.map(this::toResponseDTO)
													.collect(Collectors.toList());
		
		return new PageImpl<>(entityList, entityPage.getPageable(), entityPage.getTotalElements());
	}
}
