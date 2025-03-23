package com.meetime.hubspot.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ResponseUtils {
    public String getFullURL(HttpServletRequest request) {
        StringBuilder requestURL = new StringBuilder(request.getRequestURL().toString());
        String queryString = request.getQueryString();
        return (queryString == null)
                    ? requestURL.toString()
                    : requestURL.append('?').append(queryString).toString();

    }
}
