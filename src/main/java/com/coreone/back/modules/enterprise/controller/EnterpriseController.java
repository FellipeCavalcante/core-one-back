package com.coreone.back.modules.enterprise.controller;

import com.coreone.back.modules.enterprise.domain.EnterpriseRequest;
import com.coreone.back.modules.enterprise.dto.*;
import com.coreone.back.modules.enterprise.service.EnterpriseService;
import com.coreone.back.modules.enterprise.service.EnterpriseRequestService;
import com.coreone.back.modules.enterprise.mapper.EnterpriseMapper;
import com.coreone.back.common.util.AuthUtil;
import com.coreone.back.modules.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/enterprise")
@RequiredArgsConstructor
public class EnterpriseController {

    private final EnterpriseService service;
    private final EnterpriseRequestService requestService;
    private final EnterpriseMapper mapper;
    private final AuthUtil authUtil;

    @PostMapping("/create")
    public ResponseEntity<CreateEnterpriseResponseDTO> create(@RequestBody CreateEnterpriseRequestDTO request) {
        User user = authUtil.getAuthenticatedUser();

        var response = service.save(request, user);

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

        var response = service.update(request, user);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{enterpriseId}/request-access")
    public ResponseEntity<EnterpriseRequestResponseDTO> requestAccess(
            @PathVariable UUID enterpriseId,
            @RequestBody EnterpriseRequestCreateDTO requestDTO
    ) {
        User user = authUtil.getAuthenticatedUser();
        var response = requestService.createJoinRequest(user, enterpriseId, requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{enterpriseId}/invite/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<EnterpriseRequestResponseDTO> inviteUser(
            @PathVariable UUID enterpriseId,
            @PathVariable UUID userId,
            @RequestBody(required = false) EnterpriseRequestCreateDTO requestDTO
    ) {
        User inviter = authUtil.getAuthenticatedUser();
        var response = requestService.createInvite(inviter, userId, enterpriseId, requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{enterpriseId}/requests")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Page<EnterpriseRequest>> getEnterpriseRequests(
            @PathVariable UUID enterpriseId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        var response = requestService.getRequestsByEnterprise(enterpriseId, page, size);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/requests/{requestId}/status")
    public ResponseEntity<EnterpriseRequestResponseDTO> updateRequestStatus(
            @PathVariable UUID requestId,
            @RequestBody UpdateEnterpriseRequestStatusDTO requestDTO
    ) {
        User user = authUtil.getAuthenticatedUser();
        var response = requestService.updateStatus(requestId, requestDTO, user);
        return ResponseEntity.ok(response);
    }
}
