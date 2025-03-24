package com.meetime.hubspot.utils;

import com.meetime.hubspot.handler.exception.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@UtilityClass
public class TokenUtils {
    public String extractTokenHubSpotFromRequest(HttpServletRequest request) {
        String header = request.getHeader("X-Authorization-HubSpot");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return header;
    }

    public String extractTokenHubSpotFromRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            final String header = extractTokenHubSpotFromRequest(attributes.getRequest());
            if (header == null) {
                throw new UnauthorizedException("Authentication credentials not found!");
            }
            return header;
        }
        throw new UnauthorizedException("Authentication credentials not found!");
    }
}
