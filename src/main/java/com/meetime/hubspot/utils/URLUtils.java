package com.meetime.hubspot.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@UtilityClass
public class URLUtils {
    public String getFullURL(HttpServletRequest request) {
        StringBuilder requestURL = new StringBuilder(request.getRequestURL().toString());
        String queryString = request.getQueryString();
        return (queryString == null)
                    ? requestURL.toString()
                    : requestURL.append('?').append(queryString).toString();

    }
    public URI created(HttpServletRequest request, Object id) {
        return UriComponentsBuilder.fromHttpUrl(String.valueOf(request.getRequestURL()))
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }

    public URI created(String url, Map<String, Object> params) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        params.forEach(builder::queryParam);
        return builder.build().toUri();
    }
}
