package com.coreone.back.modules.project.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class CreateProjectRequestDTO {
    @NotBlank(message = "The field 'name' is required")
    private String name;
    @NotBlank(message = "The field 'description' is required")
    private String description;
    private String status;
    private List<UUID> subSectors;
    private List<UUID> members;
}
