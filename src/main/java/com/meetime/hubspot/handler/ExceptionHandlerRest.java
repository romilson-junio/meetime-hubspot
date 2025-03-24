package com.meetime.hubspot.handler;

import com.meetime.hubspot.handler.error.Error;
import com.meetime.hubspot.handler.error.ValidationError;
import com.meetime.hubspot.handler.exception.UnauthorizedException;
import com.meetime.hubspot.handler.exception.WebApplicationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlerRest {

    @ExceptionHandler({WebApplicationException.class})
    public ResponseEntity<Error> handle(WebApplicationException e, HttpServletRequest request) {
        log.error("Erro na requisição [{}]: {}", request.getRequestURI(), e.getMessage());
        Error error = new Error(e.getStatusCode(), e.getMessage(), request);
        return ResponseEntity.status(error.getStatus()).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
        ValidationError err = new ValidationError(HttpStatus.BAD_REQUEST, e.getBody().getDetail(), request);
        for (FieldError x : e.getBindingResult().getFieldErrors()) {
            err.addError(x.getField(), x.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Error> handle(UnauthorizedException e, HttpServletRequest request) {
        Error error = new Error(HttpStatus.UNAUTHORIZED, e.getMessage(), request);
        return ResponseEntity.status(error.getStatus()).body(error);
    }

}
