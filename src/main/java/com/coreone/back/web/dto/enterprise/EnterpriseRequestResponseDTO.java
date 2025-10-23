package com.coreone.back.web.dto.enterprise;

import lombok.Data;

import java.util.UUID;
import java.sql.Timestamp;

@Data
public class EnterpriseRequestResponseDTO {
    private UUID id;
    private String message;
    private String status;
    private String type;
    private UUID userId;
    private UUID enterpriseId;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
