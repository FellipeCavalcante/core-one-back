package com.coreone.back.modules.enterprise.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UpdateEnterpriseResponseDTO {
    private UUID id;
    private String name;
    private String description;
}
