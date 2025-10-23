package com.coreone.back.web.dto.enterprise;

import lombok.Data;

import java.util.UUID;

@Data
public class UpdateEnterpriseRequestDTO {
    private UUID id;
    private String name;
    private String description;
}
