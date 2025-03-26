package com.meetime.hubspot.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class HubSpotConfig {

    @Value("${hubspot.client-id}")
    private String clientId;

    @Value("${hubspot.client-secret}")
    private String clientSecret;

    @Value("${hubspot.redirect-uri}")
    private String redirectUri;

    @Value("${hubspot.auth-url}")
    private String authUrl;

    @Value("${hubspot.token-url}")
    private String tokenUrl;

    @Value("${hubspot.scopes}")
    private String scopes;
}
