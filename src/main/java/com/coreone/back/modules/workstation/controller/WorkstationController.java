package com.coreone.back.modules.workstation.controller;

import com.coreone.back.common.util.AuthUtil;
import com.coreone.back.modules.user.domain.User;
import com.coreone.back.modules.workstation.controller.dto.CreateWorkstationRequestDTO;
import com.coreone.back.modules.workstation.controller.dto.CreateWorkstationResponseDTO;
import com.coreone.back.modules.workstation.service.WorkstationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/workstation")
@RequiredArgsConstructor
public class WorkstationController {
    private final WorkstationService service;
    private final AuthUtil authUtil;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<CreateWorkstationResponseDTO> createWorkstation(@RequestBody CreateWorkstationRequestDTO request) {
        User user = authUtil.getAuthenticatedUser();

        var response = service.createWorkstation(request, user);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{workstationId}/delete")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Void> deleteWorkstation(@PathVariable UUID workstationId) {
        service.delete(workstationId);

        return ResponseEntity.noContent().build();
    }
}
