package com.meetime.hubspot.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class AuthController {

    private final AuthService authService;

    @GetMapping("/authorize")
    public String authorize() {
        return authService.getAuthorizationUrl();
    }


    @GetMapping("/callback")
    public Mono<ResponseEntity<Map<String, Object>>> callback(@RequestParam String code) {
        return authService.accessToken(code)
                .map(ResponseEntity::ok);
    }

}