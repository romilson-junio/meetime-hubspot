package com.meetime.hubspot.utils;

import lombok.experimental.UtilityClass;
import org.springframework.web.reactive.function.client.ClientRequest;

import java.util.Arrays;

@UtilityClass
public class RequestFilter {

    private final String[] urls = new String[] {
            "/oauth/v1/token"
    };

    public boolean ignore(ClientRequest request) {
        return Arrays.stream(urls)
                .anyMatch(request.url().getPath()::contains);
    }
}
