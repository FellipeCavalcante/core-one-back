package com.coreone.back.dto.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateUserRequestDTO {
    @NotBlank(message = "The field 'name' is required")
    private String name;
    @NotBlank(message = "The field 'password' is required")
    private String password;
    @NotBlank(message = "The field 'email' is required")
    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,10}$", message = "The e-mail is not valid")
    private String email;
    private String type;
}
