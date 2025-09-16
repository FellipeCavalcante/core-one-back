package com.findjobs.back.controller;

import com.findjobs.back.domain.User;
import com.findjobs.back.repository.UserRepository;
import com.findjobs.back.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("/reports")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public String getReports() {
        return "Relatório restrito";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") UUID id) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        User requestingUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not Found!"));

        userService.delete(id, requestingUser);

        return ResponseEntity.noContent().build();
    }
}
