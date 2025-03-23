package com.meetime.hubspot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class HubSpotClient {

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("https://api.hubapi.com")
                .build();
    }

}
