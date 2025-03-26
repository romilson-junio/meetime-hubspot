package com.meetime.hubspot.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

    @Bean
    public WebClient hubSpotClient() {
        return WebClient.builder()
                .baseUrl("https://api.hubapi.com")
                .filter(new WebClientFilter())
                .build();
    }

}
