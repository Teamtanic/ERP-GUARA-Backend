package com.guarajunior.guararp.domain.service;

import com.guarajunior.guararp.api.dto.department.request.DepartmentCreateRequest;
import com.guarajunior.guararp.api.dto.department.response.DepartmentResponse;
import com.guarajunior.guararp.domain.mapper.DepartmentMapper;
import com.guarajunior.guararp.infra.model.Department;
import com.guarajunior.guararp.infra.repository.DepartmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    public DepartmentService(DepartmentRepository departmentRepository, DepartmentMapper departmentMapper) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
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
        Department department = departmentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Curso não encontrado"));
        return departmentMapper.toResponseDTO(department);
    }

    public DepartmentResponse updateDepartment(UUID id, Map<String, Object> fields) {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Curso não encontrado"));
        BeanUtils.copyProperties(fields, department, "id");

        return departmentMapper.toResponseDTO(departmentRepository.save(department));
    }

    public void deactivateDepartment(UUID id) {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Departamento não encontrado"));

        departmentRepository.save(department);
    }
}
