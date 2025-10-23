package com.coreone.back.application;

import com.coreone.back.common.errors.LoginRequestException;
import com.coreone.back.domain.entity.User;
import com.coreone.back.domain.service.UserDomainService;
import com.coreone.back.infrastructure.email.SpringEmailService;
import com.coreone.back.infrastructure.security.CustomUserDetails;
import com.coreone.back.infrastructure.security.JwtService;
import com.coreone.back.web.dto.auth.LoginResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthApplicationService {

    private final UserDomainService userDomainService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final SpringEmailService emailService;

    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        var saved = userDomainService.registerUser(user);

        Map<String, Object> variables = Map.of(
                "name", saved.getName(),
                "link", "https://google.com"
        );

        emailService.sendEmailHtml(
                saved.getEmail(),
                "Bem-vindo ao Cone 🚀",
                "welcome_email",
                variables
        );

        return saved;
    }

    public LoginResponseDTO login(String email, String password) {
        var user = userDomainService.findByEmailOrThrow(email);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new LoginRequestException("Credentials invalid");
        }

        var token = jwtService.generateToken(new CustomUserDetails(user));

        return new LoginResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getType(),
                token
        );
    }
}