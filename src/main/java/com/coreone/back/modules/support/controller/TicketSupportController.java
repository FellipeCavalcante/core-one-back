package com.coreone.back.modules.support.controller;

import com.coreone.back.common.util.AuthUtil;
import com.coreone.back.modules.support.dto.CreateTicketSupportDTO;
import com.coreone.back.modules.support.service.TicketSupportService;
import com.coreone.back.modules.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/support/ticket")
@RequiredArgsConstructor
public class TicketSupportController {
    private final TicketSupportService service;
    private final AuthUtil authUtil;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CreateTicketSupportDTO dto) {
        User user = authUtil.getAuthenticatedUser();

        service.create(dto, user);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
