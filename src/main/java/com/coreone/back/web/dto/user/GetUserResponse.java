package com.coreone.back.web.dto.user;

import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Data
public class GetUserResponse {
    private UUID id;
    private String name;
    private String email;
    private String type;
    private UUID subSector;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
