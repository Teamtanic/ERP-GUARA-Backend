package com.guarajunior.guararp.service;

import com.guarajunior.guararp.exception.CompanyServiceException;
import com.guarajunior.guararp.mapper.DepartmentMapper;
import com.guarajunior.guararp.model.Department;
import com.guarajunior.guararp.model.dto.department.DepartmentDTO;
import com.guarajunior.guararp.model.dto.department.DepartmentResponseDTO;
import com.guarajunior.guararp.repository.DepartmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.UUID;

@Service
public class DepartmentService {
	@Autowired
	private DepartmentRepository departmentRepository;
	@Autowired
	private DepartmentMapper departmentMapper;
	
	public Page<DepartmentResponseDTO> getAllDepartments(Integer page, Integer size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Department> departmentPage = departmentRepository.findAll(pageable);
		return departmentMapper.pageToResponsePageDTO(departmentPage);
	}
	
	public DepartmentResponseDTO createDepartment(DepartmentDTO departmentDTO) {
		try {
			Department departmentToCreate = departmentMapper.toEntity(departmentDTO);
			Department createdDepartment = departmentRepository.save(departmentToCreate);
			
			return departmentMapper.toResponseDTO(createdDepartment);
		} catch(Exception e) {
    		throw new CompanyServiceException("Erro ao criar empresa: " + e.getMessage());
    	}
	}
	
	public DepartmentResponseDTO getDepartmentById(UUID id) {
		Department department = departmentRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Curso não encontrado"));
		
		return departmentMapper.toResponseDTO(department);
	}
	
	public DepartmentResponseDTO updateDepartment(UUID id, Map<String, Object> fields) {
		Department department = departmentRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Curso não encontrado"));
	
		fields.forEach((key, value) -> {
			Field field = ReflectionUtils.findField(Department.class, key);
			field.setAccessible(true);
			ReflectionUtils.setField(field, department, value);
		});
		
		departmentRepository.save(department);
		
		return departmentMapper.toResponseDTO(department);
	}
	
	public void deactivateDepartment(UUID id) {
		Department department = departmentRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Departamento não encontrado"));
	
		departmentRepository.save(department);
	}
}
