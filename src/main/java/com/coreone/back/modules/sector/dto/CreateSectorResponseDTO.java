package com.coreone.back.modules.sector.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateSectorResponseDTO {
    private UUID id;
    private String name;
}
