package com.coreone.back.modules.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserRequestDTO {
    private String name;
    @Size(min = 6, message = "The password must be at least 6 characters long")
    private String password;
    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,10}$", message = "The e-mail is not valid")
    private String email;
    @Pattern(regexp = "^(MANAGER|WORKER|CLIENT)$", message = "The field 'type' must be MANAGER or USER")
    private String type;
}
