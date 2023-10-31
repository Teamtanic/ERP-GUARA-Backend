package com.guarajunior.guararp.domain.service;

import com.guarajunior.guararp.api.dto.user.request.UserAuthenticationRequest;
import com.guarajunior.guararp.api.dto.user.request.UserRegisterRequest;
import com.guarajunior.guararp.api.dto.user.response.UserAuthenticationResponse;
import com.guarajunior.guararp.api.dto.user.response.UserResponse;
import com.guarajunior.guararp.api.error.exception.UniqueKeyViolationException;
import com.guarajunior.guararp.infra.model.User;
import com.guarajunior.guararp.infra.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final UserService userService;

    public AuthenticationService(AuthenticationManager authenticationManager, TokenService tokenService, UserRepository userRepository, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public UserAuthenticationResponse getUserToken(UserAuthenticationRequest userAuthenticationRequest) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(userAuthenticationRequest.getLogin(), userAuthenticationRequest.getPassword());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());

        return new UserAuthenticationResponse(token);
    }

    public UserResponse registerUser(String token, UserRegisterRequest userRegisterRequest) {
        boolean hasUser = userRepository.findByLogin(userRegisterRequest.getLogin()).isPresent();
        if (hasUser) throw new UniqueKeyViolationException("Este login já está em uso");

        if (token == null || token.isEmpty()) {
            return userService.createUser(userRegisterRequest);
        }

        return userService.userCreateNewAccount(userRegisterRequest, token);
    }
}
