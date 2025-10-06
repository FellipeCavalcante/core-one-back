package com.coreone.back.modules.user.controller;

import com.coreone.back.modules.user.domain.User;
import com.coreone.back.modules.user.dto.GetUserResponse;
import com.coreone.back.modules.user.mapper.UserMapper;
import com.coreone.back.modules.user.repository.UserRepository;
import com.coreone.back.modules.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    private final UserService service;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @GetMapping("/reports")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Page<GetUserResponse>> getReports(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        var users = service.findAll(page, size);

        Page<GetUserResponse> response = users.map(userMapper::toGetUserResponse);

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
