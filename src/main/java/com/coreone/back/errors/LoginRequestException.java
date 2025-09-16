package com.coreone.back.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class LoginRequestException extends ResponseStatusException {
    public LoginRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
