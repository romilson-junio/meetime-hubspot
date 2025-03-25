package com.meetime.hubspot.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ErrorMessage {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String read(String message, String property) {
        return read(message, property, message);
    }

    public String read(String message, String property, String defaultMessage) {
        try {
            return objectMapper
                    .readTree(message)
                    .get(property)
                    .asText();
        } catch (JsonProcessingException e) {
            return defaultMessage;
        }
    }

}
