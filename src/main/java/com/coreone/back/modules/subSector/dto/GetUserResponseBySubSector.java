package com.coreone.back.modules.subSector.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class GetUserResponseBySubSector {
    private UUID id;
    private String name;
    private String email;
    private String type;
}
