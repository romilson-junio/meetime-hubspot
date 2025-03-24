package com.meetime.hubspot.handler.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.meetime.hubspot.utils.ResponseUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;

@Getter
public class Error {
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;
    private Integer status;
    private String error;
    private String message;
    private String path;

    public Error(HttpStatusCode statusCode, Object message, HttpServletRequest request) {
        this.date = LocalDateTime.now();
        this.status = statusCode.value();
        this.error = ((HttpStatus) statusCode).name();
        this.message = (String) message;
        this.path = ResponseUtils.getFullURL(request);
    }
}
