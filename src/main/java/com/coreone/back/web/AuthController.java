package com.coreone.back.web;

import com.coreone.back.application.AuthApplicationService;
import com.coreone.back.web.dto.auth.LoginRequestDTO;
import com.coreone.back.web.dto.auth.LoginResponseDTO;
import com.coreone.back.web.dto.user.CreateUserRequestDTO;
import com.coreone.back.web.dto.user.CreateUserResponseDTO;
import com.coreone.back.web.mapper.AuthMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthApplicationService authService;
    private final AuthMapper mapper;

    @PostMapping("/register")
    public ResponseEntity<CreateUserResponseDTO> register(@RequestBody CreateUserRequestDTO request) {
        var user = mapper.toUser(request);
        var saved = authService.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toCreateResponse(saved));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        var response = authService.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(response);
    }
}
