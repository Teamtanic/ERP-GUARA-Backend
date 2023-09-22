package com.guarajunior.rp.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import com.guarajunior.rp.model.Department;
import com.guarajunior.rp.model.dto.department.DepartmentDTO;
import com.guarajunior.rp.model.dto.department.DepartmentResponseDTO;

@Component
public class DepartmentMapper {
	private final ModelMapper modelMapper;
	
	public DepartmentMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}
	
	public Department toEntity(DepartmentDTO departamentDTO) {
		return modelMapper.map(departamentDTO, Department.class);
	}
	
	public DepartmentDTO toDTO(Department departament) {
		return modelMapper.map(departament, DepartmentDTO.class);
	}
	
	public DepartmentResponseDTO toResponseDTO(Department departament) {
		return modelMapper.map(departament, DepartmentResponseDTO.class);
	}
	
	public Page<DepartmentResponseDTO> pageToResponsePageDTO(Page<Department> entityPage){
		List<DepartmentResponseDTO> entityList = entityPage
													.getContent()
													.stream()
													.map(this::toResponseDTO)
													.collect(Collectors.toList());
		
		return new PageImpl<>(entityList, entityPage.getPageable(), entityPage.getTotalElements());
	}
}
