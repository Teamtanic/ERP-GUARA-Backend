package com.guarajunior.guararp.domain.service;

import com.guarajunior.guararp.api.dto.department.request.DepartmentCreateRequest;
import com.guarajunior.guararp.api.dto.department.response.DepartmentResponse;
import com.guarajunior.guararp.domain.mapper.DepartmentMapper;
import com.guarajunior.guararp.infra.model.Department;
import com.guarajunior.guararp.infra.repository.DepartmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;
    private final ModelMapper mapper;

    public DepartmentService(DepartmentRepository departmentRepository, DepartmentMapper departmentMapper, ModelMapper mapper) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
        this.mapper = mapper;
    }

    public Page<DepartmentResponse> getAllDepartments(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Department> departmentPage = departmentRepository.findAll(pageable);

        return departmentMapper.pageToResponsePageDTO(departmentPage);
    }

    public DepartmentResponse createDepartment(DepartmentCreateRequest departmentCreateRequest) {
        Department departmentToCreate = departmentMapper.toEntity(departmentCreateRequest);
        Department createdDepartment = departmentRepository.save(departmentToCreate);

        return departmentMapper.toResponseDTO(createdDepartment);
    }

    public DepartmentResponse getDepartmentById(UUID id) {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Departamento não encontrado"));
        return departmentMapper.toResponseDTO(department);
    }

    public DepartmentResponse updateDepartment(UUID id, DepartmentCreateRequest departmentCreateRequest) {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Departamento não encontrado"));
        mapper.map(departmentCreateRequest, department);

        return departmentMapper.toResponseDTO(departmentRepository.save(department));
    }

    public void deactivateDepartment(UUID id) {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Departamento não encontrado"));

        departmentRepository.save(department);
    }
}
