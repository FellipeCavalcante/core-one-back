package com.coreone.back.web.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserResponseDTO {
    private UUID id;
    private String name;
    private String email;
    private String type;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
