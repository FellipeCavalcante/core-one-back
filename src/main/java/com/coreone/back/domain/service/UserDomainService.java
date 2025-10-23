package com.coreone.back.domain.service;


import com.coreone.back.common.errors.EmailAlreadyExistsException;
import com.coreone.back.common.errors.LoginRequestException;
import com.coreone.back.common.errors.NotFoundException;
import com.coreone.back.domain.entity.User;
import com.coreone.back.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserDomainService {

    private final UserRepository repository;

    public void ensureEmailIsUnique(String email) {
        repository.findByEmail(email)
                .ifPresent(existing -> {
                    throw new EmailAlreadyExistsException("Email already exists: " + email);
                });
    }

    public User registerUser(User user) {
        ensureEmailIsUnique(user.getEmail());
        return repository.save(user);
    }

    public User findByEmailOrThrow(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new LoginRequestException("Credentials invalid"));
    }

    public User findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }
}