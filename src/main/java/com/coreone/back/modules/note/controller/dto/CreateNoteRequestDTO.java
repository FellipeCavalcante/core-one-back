package com.coreone.back.modules.note.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateNoteRequestDTO(
        @NotNull(message = "Title can not be null")
        @NotBlank(message = "Title can not be blank")
        String title,
        @NotNull(message = "Content can not be null")
        @NotBlank(message = "Content can not be blank")
        String content,
        Boolean isPublic,
        UUID folderId,
        UUID workstationId
) {
}
