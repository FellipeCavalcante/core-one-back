package com.coreone.back.dto.sector;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateSectorRequestDTO {
    @NotBlank(message = "The field 'name' is required")
    private String name;
    private UUID enterprise;
}
