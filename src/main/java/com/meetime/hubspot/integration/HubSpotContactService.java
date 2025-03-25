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

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class HubSpotContactService {

    private final HubSpotClient hubSpotClient;

    public Map listAll() {
        return hubSpotClient.webClient().get()
                .uri(HubSpotURI.CONTACTS.getPath())
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> response.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(
                                        new WebApplicationException(response.statusCode(), ErrorMessage.read(errorBody, "message")))))
                .bodyToMono(Map.class)
                .block();
    }


    public String save(Contact contact) {
        return hubSpotClient.webClient().post()
                .uri(HubSpotURI.CONTACTS.getPath())
                .bodyValue(contact.toData())
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> response.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(
                                        new WebApplicationException(response.statusCode(), ErrorMessage.read(errorBody, "message")))))
                .bodyToMono(JsonNode.class)
                .map(json -> json.get("id").asText())
                .block();
    }

    public Map findById(String id) {
        return hubSpotClient.webClient().get()
                .uri(HubSpotURI.CONTACTS.getPath() + "/" + id)
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> response.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(
                                        new WebApplicationException(
                                                response.statusCode(), ErrorMessage.read(errorBody, "message", "Nenhum registro encontrado")))))
                .bodyToMono(Map.class)
                .block();
    }
}
