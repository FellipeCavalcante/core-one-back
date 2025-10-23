package com.coreone.back.domain.repository;

import com.coreone.back.domain.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    Optional<User> findByEmail(String email);
    User save(User user);

    Optional<User> findById(UUID id);
}
