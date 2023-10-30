package com.guarajunior.guararp.api.controller;

import com.guarajunior.guararp.api.dto.user.request.UserEmailRequest;
import com.guarajunior.guararp.api.dto.user.request.UserUpdateRequest;
import com.guarajunior.guararp.api.dto.user.response.UserResponse;
import com.guarajunior.guararp.domain.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/usuarios")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        Page<UserResponse> users = userService.getAllUsers(page, size);

        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        UserResponse user = userService.getUserById(id);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PatchMapping("/{id}")
    @Transactional
    public ResponseEntity<?> update(@Valid @PathVariable UUID id, @RequestBody UserUpdateRequest userUpdateRequest) {
        UserResponse updatedUser = userService.updateUser(id, userUpdateRequest);

        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    @PostMapping("/token")
    @Transactional
    public ResponseEntity<?> createTokenForNewAccount(@RequestBody UserEmailRequest email, HttpServletRequest request) {
        userService.userCreateNewAccountRequest(email, request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        userService.deactivateUser(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
