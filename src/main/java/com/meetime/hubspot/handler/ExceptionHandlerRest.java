package com.meetime.hubspot.handler;

import com.meetime.hubspot.handler.error.StandardError;
import com.meetime.hubspot.handler.error.ValidationError;
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
    public ResponseEntity<StandardError> handle(WebApplicationException e, HttpServletRequest request) {
        log.error("Erro na requisição [{}]: {}", request.getRequestURI(), e.getMessage());
        StandardError error = new StandardError(e.getStatusCode(), e.getMessage(), request);
        return ResponseEntity.status(error.getStatus()).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
        ValidationError err = new ValidationError(HttpStatus.BAD_REQUEST, e.getBody().getDetail(), request);
        for (FieldError x : e.getBindingResult().getFieldErrors()) {
            err.addError(x.getField(), x.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

}
