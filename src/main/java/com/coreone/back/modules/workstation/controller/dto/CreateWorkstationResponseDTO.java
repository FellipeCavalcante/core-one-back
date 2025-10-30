package com.coreone.back.modules.workstation.controller.dto;

import java.util.UUID;

public record CreateWorkstationResponseDTO(
        UUID id,
        String name
) {
}
