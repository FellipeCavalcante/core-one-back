package com.coreone.back.modules.folder.controller.dto;

import java.sql.Timestamp;
import java.util.UUID;

public record GetFolderResponseDTO(
        UUID id,
        String name,
        String description,
        Timestamp createdAt
) {
}
