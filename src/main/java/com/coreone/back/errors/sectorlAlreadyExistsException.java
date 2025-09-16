package com.coreone.back.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class sectorlAlreadyExistsException extends ResponseStatusException {
    public sectorlAlreadyExistsException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
