package com.alura.UserManagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiResponseException(ApiRequestException ex) {
        var badRequest = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
                ex.getMessage(),
                ex,
                badRequest,
                ZonedDateTime.now(ZoneOffset.of("-03:00"))
        );
        return new ResponseEntity<>(apiException, badRequest);
    }

}
