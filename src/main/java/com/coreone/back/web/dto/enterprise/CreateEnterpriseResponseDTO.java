package com.coreone.back.web.dto.enterprise;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CreateEnterpriseResponseDTO {
    private UUID id;
    private UUID creatorId;
    private String name;
    private String description;
    private String token;
}
