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
import com.guarajunior.rp.exception.InvalidTokenException;
import com.guarajunior.rp.mapper.UserMapper;
import com.guarajunior.rp.model.Contact;
import com.guarajunior.rp.model.Course;
import com.guarajunior.rp.model.Department;
import com.guarajunior.rp.model.PasswordResetToken;
import com.guarajunior.rp.model.RegisterToken;
import com.guarajunior.rp.model.Role;
import com.guarajunior.rp.model.User;
import com.guarajunior.rp.model.dto.contact.ContactDTO;
import com.guarajunior.rp.model.dto.user.RegisterDTO;
import com.guarajunior.rp.model.dto.user.UserEmailDTO;
import com.guarajunior.rp.model.dto.user.UserPasswordDTO;
import com.guarajunior.rp.model.dto.user.UserDTO;
import com.guarajunior.rp.model.dto.user.UserResponseDTO;
import com.guarajunior.rp.repository.ContactRepository;
import com.guarajunior.rp.repository.CourseRepository;
import com.guarajunior.rp.repository.DepartmentRepository;
import com.guarajunior.rp.repository.PasswordResetTokenRepository;
import com.guarajunior.rp.repository.RegisterTokenRepository;
import com.guarajunior.rp.repository.RoleRepository;
import com.guarajunior.rp.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

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
	private ContactRepository contactRepository;
	@Autowired
	private PasswordResetTokenRepository passwordResetTokenRepository;
	@Autowired
	private RegisterTokenRepository registerTokenRepository;
	@Autowired
	private UserMapper userMapper;
	@Autowired 
	private ContactService contactService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private PasswordResetTokenService passwordResetTokenService;
	
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
			
			// Cria o contato
	        ContactDTO contactDTO = new ContactDTO();
	        contactDTO.setEmail(registerDTO.getEmail());
	        contactDTO.setTelephone(registerDTO.getTelephone());
	        contactDTO.setCell_phone(registerDTO.getCell_phone());
	        contactDTO.setUser(createdUser);
	        contactService.createContact(contactDTO);
			
	        UserResponseDTO userResponseDTO = userMapper.toResponseDTO(createdUser);
	        userResponseDTO.setContact(contactDTO);
	        
			return userResponseDTO;
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
	
	public void resetPasswordUserRequest(UserEmailDTO userDTO, HttpServletRequest request) {
		String email = userDTO.getEmail();
		
		Contact contact = contactRepository.findByEmail(email);
		if (contact == null) {
	        throw new EntityNotFoundException("Usuario não encontrada com o email: " + email);
	    }
		User user = userRepository.findByContact(contact);
	    if (user == null) {
	        throw new EntityNotFoundException("Usuario não encontrada com o email: " + email);
	    }
	    
	    String token = UUID.randomUUID().toString();
	    createPasswordResetTokenForUser(user, token);
	    emailService.sendPasswordResetEmail(email, token, request);
	}
	
	public void userCreateNewAccountRequest(UserEmailDTO userDTO, HttpServletRequest request) {	    
	    String token = UUID.randomUUID().toString();
	    String email = userDTO.getEmail();
	    createRegisterTokenForUser(email, token);
	    emailService.sendCreateNewAccountEmail(email, token, request);
	}
	
	private void createPasswordResetTokenForUser(User user, String token) {
	    PasswordResetToken passwordResetToken = new PasswordResetToken(token, user);
	    passwordResetTokenRepository.save(passwordResetToken);
	}
	
	private void createRegisterTokenForUser(String email, String token) {
	    RegisterToken registerToken = new RegisterToken(email, token);
	    registerTokenRepository.save(registerToken);
	}
	
	public UserResponseDTO userCreateNewAccount(RegisterDTO registerDTO, String token) {	
		try {
			RegisterToken registerToken = registerTokenRepository.findByToken(token);
			
			if(registerToken == null) 
				throw new EntityNotFoundException("Token para cadastro não encontrado: " + token);
			if(registerToken.isExpired())
				throw new InvalidTokenException("Token já expirou, solicite outro");
			
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
			
			User createdUser = userRepository.save(userToCreate);
			
			// Cria o contato
	        ContactDTO contactDTO = new ContactDTO();
	        contactDTO.setEmail(registerDTO.getEmail());
	        contactDTO.setTelephone(registerDTO.getTelephone());
	        contactDTO.setCell_phone(registerDTO.getCell_phone());
	        contactDTO.setUser(createdUser);
	        contactService.createContact(contactDTO);
			
	        UserResponseDTO userResponseDTO = userMapper.toResponseDTO(createdUser);
	        userResponseDTO.setContact(contactDTO);
	        
	        registerTokenRepository.delete(registerToken);
	        
			return userResponseDTO;
		} catch(Exception e) {
    		throw new CompanyServiceException("Erro ao criar usuario: " + e.getMessage());
    	}
	}
	
	public void resetPasswordWithToken(String token, UserPasswordDTO passwordPostDTO) {
        PasswordResetToken resetToken = passwordResetTokenService.validatePasswordResetToken(token);
        
        User user = resetToken.getUser();
        String encryptedPassword = new BCryptPasswordEncoder().encode(passwordPostDTO.getPassword());
        
        user.setPassword(encryptedPassword);

        userRepository.save(user);

        passwordResetTokenRepository.delete(resetToken);
    }
	
}
