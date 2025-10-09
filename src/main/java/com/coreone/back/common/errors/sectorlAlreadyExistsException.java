package com.coreone.back.common.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class sectorlAlreadyExistsException extends RuntimeException {
    public sectorlAlreadyExistsException(String message) {
        super(message);
    }
}
