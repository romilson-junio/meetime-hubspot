package com.meetime.hubspot.auth;

import com.meetime.hubspot.config.HubSpotAdmin;
import com.meetime.hubspot.integration.HubSpotAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final HubSpotAdmin hubSpotAdmin;
    private final HubSpotAuthService hubSpotAuthService;

    public String getAuthorizationUrl() {
        return UriComponentsBuilder.fromHttpUrl(hubSpotAdmin.getAuthUrl())
                .queryParam("client_id", hubSpotAdmin.getClientId())
                .queryParam("redirect_uri", hubSpotAdmin.getRedirectUri())
                .queryParam("scope", hubSpotAdmin.getScopes())
                .toUriString();
    }

    public Mono<Map<String, Object>> accessToken(String code) {
        return hubSpotAuthService.exchangeAuthorizationCode(code);
    }

}
