package com.findjobs.back.controller;

import com.findjobs.back.domain.User;
import com.findjobs.back.dto.users.GetUserResponse;
import com.findjobs.back.mapper.UserMapper;
import com.findjobs.back.repository.UserRepository;
import com.findjobs.back.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @GetMapping("/reports")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<List<GetUserResponse>> getReports() {
        var users = service.findAll();

        List<GetUserResponse> response = users.stream()
                .map(userMapper::toGetUserResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") UUID id) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        User requestingUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not Found!"));

        service.delete(id, requestingUser);

        return ResponseEntity.noContent().build();
    }
}
