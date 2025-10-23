package com.coreone.back.web.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUserRequestDTO {
    @NotBlank(message = "The field 'name' is required")
    private String name;
    @NotBlank(message = "The field 'password' is required")
    @Size(min = 6, message = "The password must be at least 6 characters long")
    private String password;
    @NotBlank(message = "The field 'email' is required")
    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,10}$", message = "The e-mail is not valid")
    private String email;
    @Pattern(regexp = "^(ADMIN|MANAGER|WORKER|CLIENT)$", message = "The field 'type' must be ADMIN, MANAGER or USER")
    private String type;

}
