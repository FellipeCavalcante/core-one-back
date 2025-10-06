package com.coreone.back.modules.enterprise.controller;

import com.coreone.back.modules.enterprise.dto.*;
import com.coreone.back.modules.user.domain.User;
import com.coreone.back.modules.enterprise.mapper.EnterpriseMapper;
import com.coreone.back.modules.enterprise.service.EnterpriseService;
import com.coreone.back.common.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/enterprise")
@RequiredArgsConstructor
public class EnterpriseController {

    private final EnterpriseService service;
    private final EnterpriseMapper mapper;
    private final AuthUtil authUtil;

    @PostMapping("/create")
    public ResponseEntity<CreateEnterpriseResponseDTO> create(@RequestBody CreateEnterpriseRequestDTO request) {
        User user = authUtil.getAuthenticatedUser();

        System.out.println("REQUEST name: " + request.getName());
        System.out.println("REQUEST description: " + request.getDescription());
        System.out.println("REQUEST email: " + user.getName());
        System.out.println("REQUEST id: " + user.getId());

        request.setCreatorId(user.getId());

        var response = service.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<GetEnterpriseResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        var enterprises = service.getAll(page, size);

        Page<GetEnterpriseResponse> response = enterprises.map(mapper::toGetEnterpriseResponse);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/update")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<UpdateEnterpriseResponseDTO> update(@RequestBody UpdateEnterpriseRequestDTO request) {
        User user = authUtil.getAuthenticatedUser();

        var response = service.update(request, user.getId());

        return ResponseEntity.ok(response);
    }
}
