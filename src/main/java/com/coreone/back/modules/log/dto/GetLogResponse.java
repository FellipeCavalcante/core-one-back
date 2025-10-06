package com.coreone.back.modules.log.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetLogResponse {
    private UUID id;
    private UUID userId;
    private String userName;
    private String userEmail;
    private String action;
    private String entity;
    private UUID entityId;
    private String newValue;
    private LocalDateTime createdAt;
}