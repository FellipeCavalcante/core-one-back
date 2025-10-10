package com.coreone.back.modules.sector.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateSectorRequest {
    @NotBlank(message = "The field 'name' is required")
    private String name;
}
