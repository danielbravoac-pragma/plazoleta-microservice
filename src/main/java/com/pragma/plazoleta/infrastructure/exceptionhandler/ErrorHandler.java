package com.pragma.plazoleta.infrastructure.exceptionhandler;

import com.pragma.plazoleta.application.exceptions.AccessDeniedException;
import com.pragma.plazoleta.application.exceptions.OrderInProgressException;
import com.pragma.plazoleta.infrastructure.exception.DataNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericResponse> handleDefaultException(Exception ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new GenericResponse<>(new Info(HttpStatus.INTERNAL_SERVER_ERROR.value(), "SOMETHING HAPPEN - CONTACT THE IT TEAM")), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GenericResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request) {
        String message = ex.getBindingResult().getFieldErrors().stream().map(e -> e.getField() + ": " + e.getDefaultMessage()).collect(Collectors.joining(","));
        return new ResponseEntity<>(new GenericResponse<>(new Info(HttpStatus.BAD_REQUEST.value(), "SOME INVALID FIELDS", "BAD REQUEST", message)), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<GenericResponse> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new GenericResponse<>(new Info(HttpStatus.UNAUTHORIZED.value(), "UNAUTHORIZED TO MAKE THIS OPERATION")), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(OrderInProgressException.class)
    public ResponseEntity<GenericResponse> handleOrderInProgressException(OrderInProgressException ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new GenericResponse<>(new Info(HttpStatus.BAD_REQUEST.value(), "YOU HAVE AN ORDER IN PROGRESS")), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<GenericResponse> handleDataNotFoundException(DataNotFoundException ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new GenericResponse<>(new Info(HttpStatus.NOT_FOUND.value(), "DATA NOT FOUND")), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public ResponseEntity<GenericResponse> handleAccessDenied(org.springframework.security.access.AccessDeniedException ex, HttpServletRequest request) {
        return new ResponseEntity<>(new GenericResponse<>(new Info(HttpStatus.UNAUTHORIZED.value(), "DON'T HAVE PERMISSION TO ACCESS THIS OPERATION CHECK YOUR SESSION")), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<GenericResponse> handleBadCredentials(BadCredentialsException ex, HttpServletRequest request) {
        return new ResponseEntity<>(new GenericResponse<>(new Info(HttpStatus.BAD_REQUEST.value(), "CREDENTIALS ARE INCORRECT")), HttpStatus.BAD_REQUEST);
    }

}
