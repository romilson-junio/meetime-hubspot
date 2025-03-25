package com.meetime.hubspot.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class AuthController {

    private final AuthService authService;

    @GetMapping("/authorize")
    public Mono<ResponseEntity> authorize() {
        return Mono.just(ResponseEntity.ok(authService.getAuthorizationURI().toString()));
    }


    @GetMapping("/callback")
    public Mono<ResponseEntity> callback(@RequestParam String code) {
        return Mono.just(ResponseEntity.ok(authService.accessToken(code)));
    }

}