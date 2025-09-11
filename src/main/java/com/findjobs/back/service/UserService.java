package com.findjobs.back.service;

import com.findjobs.back.domain.User;
import com.findjobs.back.dto.users.LoginResponseDTO;
import com.findjobs.back.errors.EmailAlreadyExistsException;
import com.findjobs.back.errors.LoginRequestException;
import com.findjobs.back.repository.UserRepository;
import com.findjobs.back.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
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

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(userFind.getEmail())
                .password(userFind.getPassword())
                .authorities(userFind.getAuthorities())
                .build();

        String token = jwtService.generateToken(userDetails);

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
