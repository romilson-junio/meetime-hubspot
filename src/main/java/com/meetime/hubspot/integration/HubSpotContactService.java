package com.meetime.hubspot.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.meetime.hubspot.config.HubSpotClient;
import com.meetime.hubspot.dto.Contact;
import com.meetime.hubspot.enums.HubSpotURI;
import com.meetime.hubspot.handler.exception.WebApplicationException;
import com.meetime.hubspot.utils.ErrorMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class HubSpotContactService {

    private final HubSpotClient hubSpotClient;

    public Mono<JsonNode> listAll() {
        return hubSpotClient.webClient().get()
                .uri(HubSpotURI.CONTACTS.getPath())
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(
                                        new WebApplicationException(clientResponse.statusCode(), ErrorMessage.read(errorBody, "message")))))
                .bodyToMono(JsonNode.class);
    }


    public Mono<JsonNode> save(Contact contact) {
        return hubSpotClient.webClient().post()
                .uri(HubSpotURI.CONTACTS.getPath())
                .bodyValue(contact.toData())
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(
                                        new WebApplicationException(clientResponse.statusCode(), ErrorMessage.read(errorBody, "message")))))
                .bodyToMono(JsonNode.class);
    }
}
