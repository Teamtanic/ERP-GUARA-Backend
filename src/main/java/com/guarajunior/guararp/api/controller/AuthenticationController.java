package com.guarajunior.guararp.api.controller;

import com.guarajunior.guararp.api.dto.user.request.UserAuthenticationRequest;
import com.guarajunior.guararp.api.dto.user.request.UserEmailRequest;
import com.guarajunior.guararp.api.dto.user.request.UserPasswordRequest;
import com.guarajunior.guararp.api.dto.user.request.UserRegisterRequest;
import com.guarajunior.guararp.api.dto.user.response.UserAuthenticationResponse;
import com.guarajunior.guararp.api.dto.user.response.UserResponse;
import com.guarajunior.guararp.domain.service.AuthenticationService;
import com.guarajunior.guararp.domain.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserService userService;

    public AuthenticationController(AuthenticationService authenticationService, UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserAuthenticationResponse> login(@RequestBody @Valid UserAuthenticationRequest userAuthenticationRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.getUserToken(userAuthenticationRequest));
    }

    @PostMapping("/registro")
    public ResponseEntity<UserResponse> register(@RequestParam(required = false, name = "token") String token, @RequestBody @Valid UserRegisterRequest userRegisterRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.registerUser(token, userRegisterRequest));
    }

    @PostMapping("/recuperar-senha")
    public ResponseEntity<?> resetPassword(@RequestBody UserEmailRequest userDTO, HttpServletRequest request) {
        userService.resetPasswordUserRequest(userDTO, request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPasswordWithToken(@RequestParam("token") String token, @RequestBody UserPasswordRequest passwordRequest) {
        userService.resetPasswordWithToken(token, passwordRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
