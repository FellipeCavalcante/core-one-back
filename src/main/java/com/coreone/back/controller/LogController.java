package com.coreone.back.controller;

import com.coreone.back.dto.logs.GetLogResponse;
import com.coreone.back.mapper.LogMapper;
import com.coreone.back.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/logs")
@RequiredArgsConstructor
public class LogController {

    private final LogService service;
    private final LogMapper mapper;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<List<GetLogResponse>> getLogs() {
        var logs = service.findAll();

        List<GetLogResponse> response = logs.stream()
                .map(mapper::toGetLogResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<GetLogResponse> getLog(@PathVariable UUID id) {
        var log = service.findById(id);

        var response = mapper.toGetLogResponse(log);

        return ResponseEntity.ok(response);
    }

    // rotas para filtrar os tipos de logs
}