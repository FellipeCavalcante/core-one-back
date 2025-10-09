package com.coreone.back.common.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LoginRequestException extends RuntimeException {
    public LoginRequestException(String message) {
        super(message);
    }
}