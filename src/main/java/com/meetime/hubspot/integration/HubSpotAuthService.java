package com.meetime.hubspot.integration;

import com.meetime.hubspot.config.HubSpotConfig;
import com.meetime.hubspot.dto.Token;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class HubSpotAuthService {

    private final WebClient hubSpotClient;
    private final HubSpotConfig hubSpotConfig;

    public Token exchangeAuthorizationCode(String code) {
        return hubSpotClient.post()
                .uri(hubSpotConfig.getTokenUrl())
                .header("Content-Type", "application/x-www-form-urlencoded")
                .body(BodyInserters.fromFormData("grant_type", "authorization_code")
                        .with("client_id", hubSpotConfig.getClientId())
                        .with("client_secret", hubSpotConfig.getClientSecret())
                        .with("redirect_uri", hubSpotConfig.getRedirectUri())
                        .with("code", code))
                .retrieve()
                .bodyToMono(Token.class)
                .block();
    }
}
