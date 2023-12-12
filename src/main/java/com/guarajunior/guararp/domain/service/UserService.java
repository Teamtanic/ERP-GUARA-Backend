package com.guarajunior.guararp.domain.service;


import com.guarajunior.guararp.api.dto.user.request.UserEmailRequest;
import com.guarajunior.guararp.api.dto.user.request.UserPasswordRequest;
import com.guarajunior.guararp.api.dto.user.request.UserRegisterRequest;
import com.guarajunior.guararp.api.dto.user.request.UserUpdateRequest;
import com.guarajunior.guararp.api.dto.user.response.UserResponse;
import com.guarajunior.guararp.api.error.exception.EntityNotFoundException;
import com.guarajunior.guararp.api.error.exception.InvalidTokenException;
import com.guarajunior.guararp.domain.mapper.UserMapper;
import com.guarajunior.guararp.infra.model.*;
import com.guarajunior.guararp.infra.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final RoleRepository roleRepository;
    private final DepartmentRepository departmentRepository;
    private final ContactRepository contactRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final RegisterTokenRepository registerTokenRepository;
    private final UserMapper userMapper;
    private final ModelMapper mapper;
    private final EmailService emailService;
    private final PasswordResetTokenService passwordResetTokenService;

    public UserService(UserRepository userRepository, CourseRepository courseRepository, RoleRepository roleRepository, DepartmentRepository departmentRepository, ContactRepository contactRepository, PasswordResetTokenRepository passwordResetTokenRepository, RegisterTokenRepository registerTokenRepository, UserMapper userMapper, ModelMapper mapper, EmailService emailService, PasswordResetTokenService passwordResetTokenService) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.roleRepository = roleRepository;
        this.departmentRepository = departmentRepository;
        this.contactRepository = contactRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.registerTokenRepository = registerTokenRepository;
        this.userMapper = userMapper;
        this.mapper = mapper;
        this.emailService = emailService;
        this.passwordResetTokenService = passwordResetTokenService;
    }

    public Page<UserResponse> getAllUsers(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage = userRepository.findAll(pageable);
        return userMapper.pageToResponsePageDTO(userPage);
    }

    public UserResponse getUserById(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado com o ID: " + id));
        return userMapper.toResponseDTO(user);
    }

    public UserResponse updateUser(UUID id, UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado com o ID: " + id));

        if (userUpdateRequest.getCourseId() != null) {
            Course course = courseRepository.findById(userUpdateRequest.getCourseId()).orElseThrow(() -> new EntityNotFoundException("Curso não encontrado com o ID: " + userUpdateRequest.getCourseId()));
            user.setCourse(course);
        }

        if (userUpdateRequest.getRoleId() != null) {
            Role role = roleRepository.findById(userUpdateRequest.getRoleId()).orElseThrow(() -> new EntityNotFoundException("Cargo não encontrado"));
            user.setRole(role);
        }

        if (userUpdateRequest.getDepartmentId() != null) {
            Department department = departmentRepository.findById(userUpdateRequest.getDepartmentId()).orElseThrow(() -> new EntityNotFoundException("Departamento não encontrado"));
            user.setDepartment(department);
        }

        mapper.map(userUpdateRequest, user);

        return userMapper.toResponseDTO(userRepository.save(user));
    }


    public UserResponse createUser(UserRegisterRequest userRegisterRequest) {
        User userToCreate = mapper.map(userRegisterRequest, User.class);

        String encryptedPassword = new BCryptPasswordEncoder().encode(userRegisterRequest.getPassword());
        userToCreate.setPassword(encryptedPassword);

        userToCreate.setActive(true);
        userToCreate.setStatus(true);

        Course course = courseRepository.findById(userRegisterRequest.getCourseId()).orElseThrow(() -> new EntityNotFoundException("Curso não encontrado"));
        userToCreate.setCourse(course);

        Role role = roleRepository.findById(userRegisterRequest.getRoleId()).orElseThrow(() -> new EntityNotFoundException("Cargo não encontrado"));
        userToCreate.setRole(role);

        Department department = departmentRepository.findById(userRegisterRequest.getDepartmentId()).orElseThrow(() -> new EntityNotFoundException("Departamento não encontrado"));
        userToCreate.setDepartment(department);

        userRepository.save(userToCreate);

        Contact contact = mapper.map(userRegisterRequest, Contact.class);
        contact.setUser(userToCreate);

        contactRepository.save(contact);

        userToCreate.setContact(contact);
        return userMapper.toResponseDTO(userToCreate);
    }


    public void deactivateUser(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuario não encontrada com o ID: " + id));
        user.setActive(false);

        userRepository.save(user);
    }
    
    public UserResponse getUserByUsername(String login) {
    	User user = userRepository.findByLogin(login).orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado com o login: " + login));
        return userMapper.toResponseDTO(user);
    }

    public void resetPasswordUserRequest(UserEmailRequest userDTO, HttpServletRequest request) {
        String email = userDTO.getEmail();

        Contact contact = contactRepository.findByEmail(email);
        if (contact == null) {
            throw new EntityNotFoundException("Usuario não encontrada com o email: " + email);
        }
        User user = userRepository.findByContact(contact).orElseThrow(() -> new EntityNotFoundException("Usuario não encontrada com o email: " + email));

        String token = UUID.randomUUID().toString();
        createPasswordResetTokenForUser(user, token);
        emailService.sendPasswordResetEmail(email, token, request);
    }

    public void userCreateNewAccountRequest(UserEmailRequest userDTO, HttpServletRequest request) {
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

    public UserResponse userCreateNewAccount(UserRegisterRequest userRegisterRequest, String token) {
        RegisterToken registerToken = registerTokenRepository.findByToken(token);

        if (registerToken == null) throw new EntityNotFoundException("Token para cadastro não encontrado: " + token);
        if (registerToken.isExpired()) throw new InvalidTokenException("Token já expirou, solicite outro");

        User userToCreate = mapper.map(userRegisterRequest, User.class);
        String encryptedPassword = new BCryptPasswordEncoder().encode(userRegisterRequest.getPassword());
        userToCreate.setPassword(encryptedPassword);
        userToCreate.setActive(true);
        userToCreate.setStatus(true);

        if (userRegisterRequest.getCourseId() != null) {
            Course course = courseRepository.findById(userRegisterRequest.getCourseId()).orElseThrow(() -> new EntityNotFoundException("Curso não encontrado"));
            userToCreate.setCourse(course);
        }

        User createdUser = userRepository.save(userToCreate);

        Contact contact = mapper.map(userRegisterRequest, Contact.class);
        contact.setUser(createdUser);

        contactRepository.save(contact);

        createdUser.setContact(contact);

        UserResponse userResponse = userMapper.toResponseDTO(createdUser);

        registerTokenRepository.delete(registerToken);

        return userResponse;
    }

    public void resetPasswordWithToken(String token, UserPasswordRequest passwordPostDTO) {
        PasswordResetToken resetToken = passwordResetTokenService.validatePasswordResetToken(token);

        User user = resetToken.getUser();
        String encryptedPassword = new BCryptPasswordEncoder().encode(passwordPostDTO.getPassword());
        user.setPassword(encryptedPassword);

        userRepository.save(user);

        passwordResetTokenRepository.delete(resetToken);
    }

}
