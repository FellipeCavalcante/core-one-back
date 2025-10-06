package com.coreone.back.modules.enterprise.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateEnterpriseResponseDTO {
    private UUID id;
    private UUID creatorId;
    private String name;
    private String description;
    private String token;
}
