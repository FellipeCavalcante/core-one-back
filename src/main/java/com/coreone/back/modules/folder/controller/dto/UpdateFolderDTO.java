package com.coreone.back.modules.folder.controller.dto;

public record UpdateFolderDTO(
        String name,
        String description,
        Boolean isPublic
) {
}
