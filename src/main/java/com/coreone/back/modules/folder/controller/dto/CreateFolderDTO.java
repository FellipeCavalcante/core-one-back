package com.coreone.back.modules.folder.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateFolderDTO(
        @NotNull(message = "The field 'name' is required")
        @NotBlank(message = "The field 'name' is required")
        String name,
        String description,
        Boolean isPublic,
        UUID workstationId
) {
}
