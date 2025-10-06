package com.coreone.back.modules.auth.service;

import com.coreone.back.modules.user.domain.User;
import com.coreone.back.common.errors.EmailAlreadyExistsException;
import com.coreone.back.common.errors.LoginRequestException;
import com.coreone.back.modules.auth.dto.LoginResponseDTO;
import com.coreone.back.modules.auth.repository.AuthRepository;
import com.coreone.back.security.CustomUserDetails;
import com.coreone.back.security.JwtService;
import com.coreone.back.modules.log.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final LogService logService;
    private final JwtService jwtService;

    public User save(User user) {
        assertEmailDosNotExists(user.getEmail());
        return repository.save(user);
    }

    public LoginResponseDTO login(String email, String password) {
        var userFind = repository.findByEmail(email)
                .orElseThrow(() -> new LoginRequestException("Email or password is incorrect"));

        if (!passwordEncoder.matches(password, userFind.getPassword())) {
            throw new LoginRequestException("Email or password is incorrect");
        }

        CustomUserDetails userDetails = new CustomUserDetails(userFind);

        String token = jwtService.generateToken(userDetails);

        var newValue = "title: " + userFind.getEmail() + " description: User login";
        logService.create(userFind, "USER_LOGIN", "USER", userFind.getId(), newValue);

        return new LoginResponseDTO(
                userFind.getId(),
                userFind.getName(),
                userFind.getEmail(),
                userFind.getType(),
                token
        );
    }

    public void assertEmailDosNotExists(String email) {
        repository.findByEmail(email)
                .ifPresent(existingUser -> {
                    throw new EmailAlreadyExistsException("E-mail já está em uso: " + email);
                });
    }
}
