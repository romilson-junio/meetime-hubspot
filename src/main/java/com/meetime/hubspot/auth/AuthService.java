package com.meetime.hubspot.auth;

import com.meetime.hubspot.config.HubSpotAdmin;
import com.meetime.hubspot.integration.HubSpotAuthService;
import com.meetime.hubspot.utils.URLUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final HubSpotAdmin hubSpotAdmin;
    private final HubSpotAuthService hubSpotAuthService;

    public URI getAuthorizationURI() {
        return URLUtils.created(hubSpotAdmin.getAuthUrl(), Map.of(
                "client_id", hubSpotAdmin.getClientId(),
                "redirect_uri", hubSpotAdmin.getRedirectUri(),
                "scope", hubSpotAdmin.getScopes()
                ));
    }

    public Map accessToken(String code) {
        return hubSpotAuthService.exchangeAuthorizationCode(code);
    }

}
