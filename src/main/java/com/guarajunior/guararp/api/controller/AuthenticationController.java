package com.guarajunior.guararp.api.controller;

import com.guarajunior.guararp.api.dto.user.request.UserAuthenticationRequest;
import com.guarajunior.guararp.api.dto.user.request.UserEmailRequest;
import com.guarajunior.guararp.api.dto.user.request.UserPasswordRequest;
import com.guarajunior.guararp.api.dto.user.request.UserRegisterRequest;
import com.guarajunior.guararp.api.dto.user.response.UserAuthenticationResponse;
import com.guarajunior.guararp.api.dto.user.response.UserResponse;
import com.guarajunior.guararp.api.error.ErrorResponse;
import com.guarajunior.guararp.api.error.exception.InvalidTokenException;
import com.guarajunior.guararp.domain.service.TokenService;
import com.guarajunior.guararp.domain.service.UserService;
import com.guarajunior.guararp.infra.model.User;
import com.guarajunior.guararp.infra.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserAuthenticationRequest userAuthenticationRequest) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(userAuthenticationRequest.getLogin(), userAuthenticationRequest.getPassword());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.status(HttpStatus.OK).body(new UserAuthenticationResponse(token));
    }

    @PostMapping("/registro")
    public ResponseEntity<?> register(@RequestParam("token") String token, @RequestBody @Valid UserRegisterRequest userRegisterRequest) {
        try {

            if (token == null || token.isEmpty()) {
                if (userRepository.findByLogin(userRegisterRequest.getLogin()) != null)
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                UserResponse user = userService.createUser(userRegisterRequest);
                return ResponseEntity.status(HttpStatus.OK).body(user);
            } else {
                if (userRepository.findByLogin(userRegisterRequest.getLogin()) != null)
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                UserResponse user = userService.userCreateNewAccount(userRegisterRequest, token);
                return ResponseEntity.status(HttpStatus.OK).body(user);
            }

        } catch (Exception e) {
            String errorMessage = "Erro ao recuperar criar usuario";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message(errorMessage).build());
        }
    }

    @PostMapping("/recuperar-senha")
    public ResponseEntity<?> resetPassword(@RequestParam("token") String token, @RequestBody UserEmailRequest userDTO, HttpServletRequest request) {
        userService.resetPasswordUserRequest(userDTO, request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPasswordWithToken(@RequestParam("token") String token, @RequestBody UserPasswordRequest passwordRequest) {
        try {
            userService.resetPasswordWithToken(token, passwordRequest);

            return ResponseEntity.status(HttpStatus.OK).body("Redefinição de senha bem-sucedida");
        } catch (InvalidTokenException e) {
            String errorMessage = "Token inválido ou expirado";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.builder().status(HttpStatus.BAD_REQUEST).message(errorMessage).build());
        } catch (Exception e) {
            String errorMessage = "Erro ao redefinir a senha";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message(errorMessage).build());
        }
    }
}
