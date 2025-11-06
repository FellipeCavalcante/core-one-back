package com.coreone.back.modules.support.dto;

import java.sql.Timestamp;
import java.util.UUID;

public record GetTicketsSupportResponseDTO(
        UUID id,
        String title,
        String status,
        String type,
        Timestamp createdAt
) {
}
