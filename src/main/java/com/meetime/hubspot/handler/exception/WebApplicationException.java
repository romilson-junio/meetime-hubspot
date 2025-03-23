package com.meetime.hubspot.handler.exception;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

public class WebApplicationException extends RuntimeException{

    @Getter
    private HttpStatusCode statusCode;

    public WebApplicationException(HttpStatusCode httpStatusCode, String message) {
        super(message);
        this.statusCode = httpStatusCode;
    }
}
