package com.example.persons.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PropertyNotAllowed extends RuntimeException {
    public PropertyNotAllowed(String property) {
        super(property + " not allowed to be set by the client");
    }
}
