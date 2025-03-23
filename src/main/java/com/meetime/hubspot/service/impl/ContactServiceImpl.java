package com.meetime.hubspot.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.meetime.hubspot.auth.AuthService;
import com.meetime.hubspot.config.HubSpotClient;
import com.meetime.hubspot.dto.Contatc;
import com.meetime.hubspot.enums.HubSpotURI;
import com.meetime.hubspot.handler.exception.WebApplicationException;
import com.meetime.hubspot.service.ContactService;
import com.meetime.hubspot.utils.ErrorMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContactServiceImpl implements ContactService {

    private final HubSpotClient hubSpotClient;
    private final AuthService authService;

    @Override
    public Mono<JsonNode> list() {
        return hubSpotClient.webClient().get()
                .uri(HubSpotURI.CONTACTS.getPath())
                .headers(headers -> headers.setBearerAuth(authService.getAccessToken()))
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(
                                        new WebApplicationException(clientResponse.statusCode(), ErrorMessage.read(errorBody, "message")))))
                .bodyToMono(JsonNode.class);
    }

    @Override
    public Mono<JsonNode> create(Contatc contatc) {
        return hubSpotClient.webClient().post()
                .uri(HubSpotURI.CONTACTS.getPath())
                .headers(headers -> headers.setBearerAuth(authService.getAccessToken()))
                .bodyValue(contatc.toData())
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(
                                        new WebApplicationException(clientResponse.statusCode(), ErrorMessage.read(errorBody, "message")))))
                .bodyToMono(JsonNode.class);
    }

}