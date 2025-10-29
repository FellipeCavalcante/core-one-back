package com.coreone.back.modules.user.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Data
public class GetUserResponse {
    private UUID id;
    private String name;
    private String email;
    private String type;
    List<UUID> subSectors;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
