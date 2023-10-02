package com.guarajunior.rp.service;


import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.guarajunior.rp.exception.CompanyServiceException;
import com.guarajunior.rp.exception.EntityNotFoundException;
import com.guarajunior.rp.mapper.UserMapper;
import com.guarajunior.rp.model.Course;
import com.guarajunior.rp.model.Department;
import com.guarajunior.rp.model.Role;
import com.guarajunior.rp.model.User;
import com.guarajunior.rp.model.dto.user.RegisterDTO;
import com.guarajunior.rp.model.dto.user.UserDTO;
import com.guarajunior.rp.model.dto.user.UserResponseDTO;
import com.guarajunior.rp.repository.CourseRepository;
import com.guarajunior.rp.repository.DepartmentRepository;
import com.guarajunior.rp.repository.RoleRepository;
import com.guarajunior.rp.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private DepartmentRepository departmentRepository;
	@Autowired
	private UserMapper userMapper;
	
	public Page<UserResponseDTO> getAllUsers(Integer page, Integer size){
		Pageable pageable = PageRequest.of(page, size);
    	Page<User> userPage = userRepository.findAll(pageable);
    	return userMapper.pageToResponsePageDTO(userPage);
	}
	
	public UserResponseDTO getUserById(UUID id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado com o ID: " + id));
	
		return userMapper.toResponseDTO(user);
	}
	
	public UserResponseDTO updateUser(UUID id, UserDTO userDTO) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado com o ID: " + id));
	
	    if (userDTO.getName() != null) {
	        user.setName(userDTO.getName());
	    }

	    if (userDTO.getPassword() != null) {
	        user.setPassword(userDTO.getPassword());
	    }
	    
	    if (userDTO.getProntuary() != null) {
	        user.setProntuary(userDTO.getProntuary());
	    }

	    if (userDTO.getCourseId() != null) {
	        Course course = courseRepository.findById(userDTO.getCourseId())
	                .orElseThrow(() -> new EntityNotFoundException("Curso não encontrado com o ID: " + userDTO.getCourseId()));
	        user.setCourse(course);
	    }
	    
	    if(userDTO.getRoleId() != null) {
			Role role = roleRepository.findById(userDTO.getRoleId())
					.orElseThrow(() -> new EntityNotFoundException("Cargo não encontrado"));;
			user.setRole(role);
		}
		
		if(userDTO.getDepartmentId() != null) {
			Department department = departmentRepository.findById(userDTO.getDepartmentId())
					.orElseThrow(() -> new EntityNotFoundException("Departamento não encontrado"));;
			user.setDepartment(department);
		}
		
		userRepository.save(user);
		
		return userMapper.toResponseDTO(user);
	}
	
	
	public UserResponseDTO createUser(RegisterDTO registerDTO) {	
		try {
			User userToCreate = new User();
			String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.getPassword());
			userToCreate.setName(registerDTO.getName());
			userToCreate.setLogin(registerDTO.getLogin());
			userToCreate.setPassword(encryptedPassword);
			userToCreate.setProntuary(registerDTO.getProntuary());
			userToCreate.setActive(true);
			userToCreate.setStatus(true);
			
			if(registerDTO.getCourseId() != null) {				
				Course course = courseRepository.findById(registerDTO.getCourseId())
						.orElseThrow(() -> new EntityNotFoundException("Curso não encontrado"));
				userToCreate.setCourse(course);
			}
			
			if(registerDTO.getRoleId() != null) {
				Role role = roleRepository.findById(registerDTO.getRoleId())
						.orElseThrow(() -> new EntityNotFoundException("Cargo não encontrado"));
				userToCreate.setRole(role);
			}
			
			if(registerDTO.getDepartmentId() != null) {
				Department department = departmentRepository.findById(registerDTO.getDepartmentId())
						.orElseThrow(() -> new EntityNotFoundException("Departamento não encontrado"));
				userToCreate.setDepartment(department);
			}
			
			User createdUser = userRepository.save(userToCreate);
			
			return userMapper.toResponseDTO(createdUser);
		} catch(Exception e) {
    		throw new CompanyServiceException("Erro ao criar usuario: " + e.getMessage());
    	}
	}
	
	
	public void deactivateUser(UUID id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Usuario não encontrada com o ID: " + id));
	
		user.setActive(false);
		
		userRepository.save(user);
	}
}
