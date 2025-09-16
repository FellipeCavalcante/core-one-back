package com.coreone.back.dto.users;

import com.coreone.back.domain.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginResponseDTO {
    private UUID id;
    private String name;
    private String email;
    private UserType type;
    private String token;
}