package com.architect.api;

import com.architect.exceptions.DtoError;
import com.architect.exceptions.EmailOrPasswordNotMatchException;
import com.architect.exceptions.EntityNotFoundException;
import com.architect.exceptions.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>("not valid due to validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public DtoError handleNotFoundException(EntityNotFoundException exception) {
        return DtoError.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message(exception.getMessage())
                .build();
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public DtoError handleUnauthorizedException() {
        return DtoError.builder()
                .code(HttpStatus.UNAUTHORIZED.value())
                .message("User not authorized!")
                .build();
    }

    @ExceptionHandler(EmailOrPasswordNotMatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public DtoError handleEmailOrPasswordNotMatchException() {
        return DtoError.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message("User email or password is not valid!")
                .build();
    }
}
