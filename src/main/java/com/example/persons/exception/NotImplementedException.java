package com.example.persons.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class NotImplementedException extends RuntimeException {

    public NotImplementedException(String methodName) {
        super(methodName + " not implemented");
    }
}
