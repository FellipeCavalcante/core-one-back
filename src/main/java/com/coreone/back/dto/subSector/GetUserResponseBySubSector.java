package com.coreone.back.dto.subSector;

import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Data
public class GetUserResponseBySubSector {
    private UUID id;
    private String name;
    private String email;
    private String type;
}
