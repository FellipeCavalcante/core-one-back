package com.coreone.back.modules.folder.controller.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record GetFolderItemResponseDTO(
        UUID id,
        UUID userId,
        String fileName,
        String storageKey,
        String contentType,
        String urlPublic,
        OffsetDateTime createdAt
) {
}
