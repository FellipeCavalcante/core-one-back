package com.findjobs.back.service;

import com.findjobs.back.domain.User;
import com.findjobs.back.domain.enums.UserType;
import com.findjobs.back.dto.users.LoginResponseDTO;
import com.findjobs.back.errors.EmailAlreadyExistsException;
import com.findjobs.back.errors.LoginRequestException;
import com.findjobs.back.errors.NotFoundException;
import com.findjobs.back.repository.UserRepository;
import com.findjobs.back.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
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

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(userFind.getEmail())
                .password(userFind.getPassword())
                .authorities(userFind.getAuthorities())
                .build();

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

    public void delete(UUID id, User requestingUser) {
        User userToDelete = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        System.out.println("USUÁRIO QUE ESTÁ DELETANDO:  " + requestingUser.getType() + " type: " + requestingUser.getId());
        System.out.println("USUÁRIO QUE VAI SER DELETADO: " + userToDelete.getType() + " type: " + userToDelete.getId());
        System.out.println("ID DO PARAMETRO: " + id);

        System.out.println("TIPO DO USUÁRIO REQUEST: '" + requestingUser.getType() + "'");

        System.out.println("USUÁRIO REQUEST - Tipo: '" + requestingUser.getType() + "'");
        System.out.println("COMPARAÇÃO COM 'ADMIN': " + "ADMIN".equals(requestingUser.getType()));

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
}
