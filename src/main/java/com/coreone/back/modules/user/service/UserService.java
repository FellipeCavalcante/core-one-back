package com.coreone.back.modules.user.service;

import com.coreone.back.modules.user.domain.User;
import com.coreone.back.modules.user.domain.enums.UserType;
import com.coreone.back.common.errors.EmailAlreadyExistsException;
import com.coreone.back.common.errors.NotFoundException;
import com.coreone.back.modules.user.repository.UserRepository;
import com.coreone.back.modules.log.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final LogService logService;

    public User save(User user) {
        return repository.save(user);
    }

    public Page<User> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable);
    }

    public void delete(UUID id, User requestingUser) {
        User userToDelete = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        boolean isAdmin = requestingUser.getType() == UserType.ADMIN;
        boolean isSelf = requestingUser.getId().equals(id);

        if (!isAdmin && !isSelf) {
            throw new RuntimeException("You don't have permission to delete this user");
        }

        var value = "Deleted user: " + userToDelete.getEmail();

        if (isSelf) {
            logService.create(null, "DELETE_USER", "USER", userToDelete.getId(), value);
        } else {
            logService.create(requestingUser, "DELETE_USER", "USER", userToDelete.getId(), value);
        }

        repository.deleteById(id);
    }

    public void assertEmailDosNotExists(String email) {
        repository.findByEmail(email)
                .ifPresent(existingUser -> {
                    throw new EmailAlreadyExistsException("E-mail já está em uso: " + email);
                });
    }

    public User findById(UUID id) {
        return repository.findById(id).orElseThrow(
                () -> new NotFoundException("User not found")
        );
    }
}
