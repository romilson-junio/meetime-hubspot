package com.meetime.hubspot.auth;

import com.meetime.hubspot.config.HubSpotConfig;
import com.meetime.hubspot.dto.Token;
import com.meetime.hubspot.integration.HubSpotAuthService;
import com.meetime.hubspot.utils.URLUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final HubSpotConfig hubSpotConfig;
    private final HubSpotAuthService hubSpotAuthService;

    public URI getAuthorizationURI() {
        return URLUtils.created(hubSpotConfig.getAuthUrl(), Map.of(
                "client_id", hubSpotConfig.getClientId(),
                "redirect_uri", hubSpotConfig.getRedirectUri(),
                "scope", hubSpotConfig.getScopes()
                ));
    }

    public Token accessToken(String code) {
        return hubSpotAuthService.exchangeAuthorizationCode(code);
    }

}
