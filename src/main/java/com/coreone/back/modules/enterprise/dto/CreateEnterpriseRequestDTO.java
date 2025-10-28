package com.coreone.back.modules.enterprise.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateEnterpriseRequestDTO {
    @NotBlank(message = "The field 'name' is required")
    private String name;
    @NotBlank(message = "The field 'description' is required")
    private String description;
}
