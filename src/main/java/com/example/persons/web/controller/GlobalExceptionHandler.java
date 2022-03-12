package com.example.persons.web.controller;

import com.example.persons.web.model.ErrorDto;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  private static final String FIELD_MESSAGE_SEPARATOR = ": ";
  private static final String DESCRIPTION_SEPARATOR = "=";

  @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
  public ResponseEntity<ErrorDto> handleException(BindException bindException, WebRequest request) {
    var errorDtoBuilder =
        getBaseErrorBuilder(request)
            .status(HttpStatus.BAD_REQUEST.value())
            .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .reason(buildReason(bindException));

    return new ResponseEntity<>(errorDtoBuilder.build(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({ResponseStatusException.class})
  public ResponseEntity<ErrorDto> handleException(
      ResponseStatusException responseStatusException, WebRequest request) {
    var errorDtoBuilder =
        getBaseErrorBuilder(request)
            .status(responseStatusException.getStatus().value())
            .error(responseStatusException.getStatus().getReasonPhrase())
            .reason(responseStatusException.getReason());

    return new ResponseEntity<>(errorDtoBuilder.build(), responseStatusException.getStatus());
  }

  @ExceptionHandler({InvalidFormatException.class})
  public ResponseEntity<ErrorDto> handleException(
      InvalidFormatException invalidFormatException, WebRequest request) {
    var reason =
        String.format(
            "%s is not compatible with %s",
            invalidFormatException.getValue(), invalidFormatException.getTargetType());

    var errorDtoBuilder =
        getBaseErrorBuilder(request)
            .status(HttpStatus.BAD_REQUEST.value())
            .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .reason(reason);

    return new ResponseEntity<>(errorDtoBuilder.build(), HttpStatus.BAD_REQUEST);
  }

  private String buildReason(BindException bindException) {
    var stringBuilder = new StringBuilder();
    bindException
        .getFieldErrors()
        .forEach(
            err ->
                stringBuilder
                    .append(err.getField())
                    .append(FIELD_MESSAGE_SEPARATOR)
                    .append(err.getDefaultMessage()));

    bindException
        .getGlobalErrors()
        .forEach(
            err ->
                stringBuilder
                    .append(err.getObjectName())
                    .append(FIELD_MESSAGE_SEPARATOR)
                    .append(err.getDefaultMessage()));

    return stringBuilder.toString();
  }

  private ErrorDto.ErrorDtoBuilder getBaseErrorBuilder(WebRequest request) {
    var requestPath = request.getDescription(false).split(DESCRIPTION_SEPARATOR)[1];
    return ErrorDto.builder().timestamp(new Date().getTime()).path(requestPath);
  }
}
