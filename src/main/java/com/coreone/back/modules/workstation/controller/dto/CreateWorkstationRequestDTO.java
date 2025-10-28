package com.coreone.back.modules.workstation.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateWorkstationRequestDTO(
        @NotNull(message = "The field 'name' can not be null")
        @NotBlank(message = "The field 'name' can no be blank")
        String name
) {
}
