package com.architect.api;

import com.architect.exceptions.*;
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
        return buildError(HttpStatus.NOT_FOUND, exception);
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public DtoError handleServiceException(ServiceException exception) {
        return buildError(HttpStatus.BAD_REQUEST, exception);
    }

    @ExceptionHandler(IdempotenceException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public DtoError handleConflictException(IdempotenceException exception) {
        return buildError(HttpStatus.CONFLICT, exception);
    }

    private DtoError buildError(HttpStatus status, Exception exception) {
        return DtoError.builder()
                .code(status.value())
                .message(exception.getMessage())
                .build();
    }
}
