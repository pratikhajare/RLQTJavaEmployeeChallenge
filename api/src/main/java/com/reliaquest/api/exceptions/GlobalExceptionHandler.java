package com.reliaquest.api.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(Exception ex, WebRequest request) {
        log.error("Exception occurred. Root cause is {} and StackTrace is {} ", ex.getMessage(), ex.getStackTrace());
        ex.printStackTrace();
        return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {BaseException.class})
    protected ResponseEntity<String> handleBaseException(BaseException ex, WebRequest request) {
        log.error("Exception occurred. Root cause is {} and StackTrace is {} ", ex.getMessage(), ex.getStackTrace());
        ex.printStackTrace();

        return new ResponseEntity<>("An error occurred: " + ex.getMessage(), ex.getHttpStatus());
    }

    @ExceptionHandler(value = {EntityNotFoundException.class})
    protected ResponseEntity<String> handleBaseException(EntityNotFoundException ex, WebRequest request) {
        log.error("Exception occurred. Root cause is {} and StackTrace is {} ", ex.getMessage(), ex.getStackTrace());
        ex.printStackTrace();

        return new ResponseEntity<>("An error occurred: " + ex.getMessage(), ex.getHttpStatus());
    }
}
