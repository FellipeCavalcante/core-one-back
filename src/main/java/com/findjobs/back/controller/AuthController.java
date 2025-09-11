package com.findjobs.back.controller;

import com.findjobs.back.domain.enums.UserType;
import com.findjobs.back.dto.users.CreateUserRequestDTO;
import com.findjobs.back.dto.users.CreateUserResponseDTO;
import com.findjobs.back.dto.users.LoginRequestDTO;
import com.findjobs.back.dto.users.LoginResponseDTO;
import com.findjobs.back.errors.LoginRequestException;
import com.findjobs.back.mapper.UserMapper;
import com.findjobs.back.security.JwtService;
import com.findjobs.back.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService service;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<CreateUserResponseDTO> register(@RequestBody CreateUserRequestDTO request) {
        System.out.println("Request received: " + request.toString());
        System.out.println("Password received: " + request.getPassword());
        System.out.println("Email received: " + request.getEmail());

        if (request.getPassword() == null) {
            return ResponseEntity.badRequest().body(null);
        }

        UserType userType = UserType.valueOf(request.getType().toUpperCase());

        var user = userMapper.toUser(request);
        user.setType(userType);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        var userSaved = service.save(user);
        var createUserResponse = userMapper.toCreateResponse(userSaved);

        return ResponseEntity.status(HttpStatus.CREATED).body(createUserResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        try {
            var response = service.login(request.getEmail(), request.getPassword());
            return ResponseEntity.ok(response);
        } catch (LoginRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}