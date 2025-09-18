package com.coreone.back.dto.enterprise;

import lombok.Data;

import java.util.UUID;

@Data
public class GetEnterpriseResponse {
    private UUID id;
    private String name;
    private String description;
}
