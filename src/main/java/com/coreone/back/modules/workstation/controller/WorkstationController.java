package com.coreone.back.modules.workstation.controller;

import com.coreone.back.common.util.AuthUtil;
import com.coreone.back.modules.user.domain.User;
import com.coreone.back.modules.workstation.controller.dto.CreateWorkstationRequestDTO;
import com.coreone.back.modules.workstation.domain.Workstation;
import com.coreone.back.modules.workstation.service.WorkstationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/workstation")
@RequiredArgsConstructor
public class WorkstationController {
    private final WorkstationService service;
    private final AuthUtil authUtil;

    @PostMapping("/create")
    public ResponseEntity<Workstation> createWorkstation(@RequestBody CreateWorkstationRequestDTO request) {
        User user = authUtil.getAuthenticatedUser();

        var response = service.createWorkstation(request, user);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
