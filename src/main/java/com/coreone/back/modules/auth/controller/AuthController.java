package com.coreone.back.modules.auth.controller;

import com.coreone.back.modules.user.domain.enums.UserType;
import com.coreone.back.modules.user.dto.CreateUserRequestDTO;
import com.coreone.back.modules.user.dto.CreateUserResponseDTO;
import com.coreone.back.common.errors.LoginRequestException;
import com.coreone.back.modules.auth.dto.LoginRequestDTO;
import com.coreone.back.modules.auth.dto.LoginResponseDTO;
import com.coreone.back.modules.auth.mapper.AuthMapper;
import com.coreone.back.modules.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;
    private final PasswordEncoder passwordEncoder;
    private final AuthMapper mapper;

    @PostMapping("/register")
    public ResponseEntity<CreateUserResponseDTO> register(@RequestBody CreateUserRequestDTO request) {
        System.out.println("Request received: " + request.toString());
        System.out.println("Password received: " + request.getPassword());
        System.out.println("Email received: " + request.getEmail());

        if (request.getPassword() == null) {
            return ResponseEntity.badRequest().body(null);
        }

        UserType userType = UserType.valueOf(request.getType().toUpperCase());

        var user = mapper.toUser(request);
        user.setType(userType);
        user.setPassword(passwordEncoder.encode(request.getPassword()));


        var userSaved = service.save(user);
        var createUserResponse = mapper.toCreateResponse(userSaved);

        return ResponseEntity.status(HttpStatus.CREATED).body(createUserResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        var response = service.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(response);
    }
}