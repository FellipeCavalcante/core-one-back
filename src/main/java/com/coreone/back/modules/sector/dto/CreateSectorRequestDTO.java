package com.coreone.back.modules.sector.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateSectorRequestDTO {
    @NotBlank(message = "The field 'name' is required")
    private String name;
}
