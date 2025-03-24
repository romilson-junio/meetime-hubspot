package com.meetime.hubspot.integration;

import com.meetime.hubspot.config.HubSpotAdmin;
import com.meetime.hubspot.config.HubSpotClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class HubSpotAuthService {

    private final HubSpotClient hubSpotClient;
    private final HubSpotAdmin hubSpotAdmin;

    public Mono exchangeAuthorizationCode(String code) {
        return hubSpotClient.webClient().post()
                .uri(hubSpotAdmin.getTokenUrl())
                .header("Content-Type", "application/x-www-form-urlencoded")
                .body(BodyInserters.fromFormData("grant_type", "authorization_code")
                        .with("client_id", hubSpotAdmin.getClientId())
                        .with("client_secret", hubSpotAdmin.getClientSecret())
                        .with("redirect_uri", hubSpotAdmin.getRedirectUri())
                        .with("code", code))
                .retrieve()
                .bodyToMono(Map.class);
    }
}
