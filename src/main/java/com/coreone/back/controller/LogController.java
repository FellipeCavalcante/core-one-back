package com.coreone.back.controller;

import com.coreone.back.dto.logs.GetLogResponse;
import com.coreone.back.mapper.LogMapper;
import com.coreone.back.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/logs")
@RequiredArgsConstructor
public class LogController {

    private final LogService service;
    private final LogMapper mapper;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Page<GetLogResponse>> getLogs(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "20") int size) {
        var logs = service.findAll(page, size);

        Page<GetLogResponse> response = logs.map(mapper::toGetLogResponse);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<GetLogResponse> getLog(@PathVariable UUID id) {
        var log = service.findById(id);

        var response = mapper.toGetLogResponse(log);

        return ResponseEntity.ok(response);
    }
}