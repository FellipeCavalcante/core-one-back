package com.coreone.back.modules.support.dto;

public record CreateTicketSupportDTO(
        String title,
        String description,
        String status,
        String type
) {
}
