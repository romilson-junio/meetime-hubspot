package com.meetime.hubspot.auth;

import com.meetime.hubspot.config.HubSpotAdmin;
import com.meetime.hubspot.config.HubSpotClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final HubSpotAdmin hubSpotAdmin;
    private final HubSpotClient hubSpotClient;
    private final AtomicReference<String> accessToken = new AtomicReference<>();

    public String getAuthorizationUrl() {
        return UriComponentsBuilder.fromHttpUrl(hubSpotAdmin.getAuthUrl())
                .queryParam("client_id", hubSpotAdmin.getClientId())
                .queryParam("redirect_uri", hubSpotAdmin.getRedirectUri())
                .queryParam("scope", hubSpotAdmin.getScopes())
                .toUriString();
    }

    public Mono<Void> exchangeAuthorizationCode(String code) {
        return hubSpotClient.webClient().post()
                .uri(hubSpotAdmin.getTokenUrl())
                .header("Content-Type", "application/x-www-form-urlencoded")
                .body(BodyInserters.fromFormData("grant_type", "authorization_code")
                        .with("client_id", hubSpotAdmin.getClientId())
                        .with("client_secret", hubSpotAdmin.getClientSecret())
                        .with("redirect_uri", hubSpotAdmin.getRedirectUri())
                        .with("code", code))
                .retrieve()
                .bodyToMono(Map.class)
                .doOnNext(response -> {
                    accessToken.set((String) response.get("access_token"));
                })
                .then();
    }

    public String getAccessToken() {
        return accessToken.get();
    }
}
