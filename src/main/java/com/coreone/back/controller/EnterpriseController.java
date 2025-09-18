package com.coreone.back.controller;

import com.coreone.back.domain.User;
import com.coreone.back.dto.enterprise.CreateEnterpriseRequestDTO;
import com.coreone.back.dto.enterprise.CreateEnterpriseResponseDTO;
import com.coreone.back.dto.enterprise.GetEnterpriseResponse;
import com.coreone.back.mapper.EnterpriseMapper;
import com.coreone.back.repository.UserRepository;
import com.coreone.back.service.EnterpriseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/enterprise")
@RequiredArgsConstructor
public class EnterpriseController {

    private final EnterpriseService service;
    private final UserRepository userRepository;
    private final EnterpriseMapper mapper;

    @PostMapping("/create")
    public ResponseEntity<CreateEnterpriseResponseDTO> create(@RequestBody CreateEnterpriseRequestDTO request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not Found!"));

        System.out.println("REQUEST name: " + request.getName());
        System.out.println("REQUEST description: " + request.getDescription());
        System.out.println("REQUEST email: " + auth.getName());
        System.out.println("REQUEST id: " + user.getId());

        request.setCreatorId(user.getId());

        var response = service.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<GetEnterpriseResponse>> getAll() {
        var enterprises = service.getAll();

        List<GetEnterpriseResponse> response = enterprises.stream()
                .map(mapper::toGetEnterpriseResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}
