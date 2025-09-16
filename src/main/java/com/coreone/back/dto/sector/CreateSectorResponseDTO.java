package com.coreone.back.dto.sector;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateSectorResponseDTO {
    private UUID id;
    private String name;
}
