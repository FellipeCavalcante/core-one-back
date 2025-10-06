package com.coreone.back.modules.subSector.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateSubSectorResponseDTO {
    private UUID id;
    @NotBlank(message = "The field 'name' is required")
    private String name;
    @NotNull(message = "The field 'subSector' is required")
    private UUID sector;
}
