package com.guarajunior.guararp.domain.service;

import com.guarajunior.guararp.api.dto.department.request.DepartmentCreateRequest;
import com.guarajunior.guararp.api.dto.department.response.DepartmentResponse;
import com.guarajunior.guararp.domain.mapper.DepartmentMapper;
import com.guarajunior.guararp.infra.model.Department;
import com.guarajunior.guararp.infra.repository.DepartmentRepository;
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

        fields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Department.class, key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, department, value);
        });

        departmentRepository.save(department);

        return departmentMapper.toResponseDTO(department);
    }

    public void deactivateDepartment(UUID id) {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Departamento não encontrado"));

        departmentRepository.save(department);
    }
}
