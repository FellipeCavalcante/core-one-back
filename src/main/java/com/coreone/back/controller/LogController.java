package com.coreone.back.controller;

import com.coreone.back.dto.logs.GetLogResponse;
import com.coreone.back.mapper.LogMapper;
import com.coreone.back.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/logs")
@RequiredArgsConstructor
public class LogController {

    private final LogService service;
    private final LogMapper logMapper; // Adicione esta linha

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<List<GetLogResponse>> getLogs() {
        var logs = service.findAll();

        List<GetLogResponse> response = logs.stream()
                .map(logMapper::toGetLogResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}