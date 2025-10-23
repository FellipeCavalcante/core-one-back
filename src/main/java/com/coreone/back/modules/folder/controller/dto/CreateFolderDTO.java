package com.coreone.back.modules.folder.controller.dto;

import java.util.UUID;

public record CreateFolderDTO(
        String name,
        String description,
        Boolean isPublic,
        UUID enterpriseId
) {
}
