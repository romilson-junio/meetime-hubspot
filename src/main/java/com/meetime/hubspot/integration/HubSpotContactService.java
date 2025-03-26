package com.meetime.hubspot.integration;

import com.meetime.hubspot.dto.Contact;
import com.meetime.hubspot.dto.ContactResponse;
import com.meetime.hubspot.dto.ContactResponseWrapper;
import com.meetime.hubspot.enums.HubSpotURI;
import com.meetime.hubspot.handler.exception.WebApplicationException;
import com.meetime.hubspot.utils.ErrorMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HubSpotContactService {

    private final WebClient hubSpotClient;

    public List<ContactResponse> listAll() {
        return hubSpotClient.get()
                .uri(HubSpotURI.CONTACTS.getPath())
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> response.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(
                                        new WebApplicationException(response.statusCode(), ErrorMessage.read(errorBody, "message")))))
                .bodyToMono(ContactResponseWrapper.class)
                .map(ContactResponseWrapper::getContacts)
                .block();
    }


    public String save(Contact contact) {
        return hubSpotClient.post()
                .uri(HubSpotURI.CONTACTS.getPath())
                .bodyValue(contact.toData())
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> response.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(
                                        new WebApplicationException(response.statusCode(), ErrorMessage.read(errorBody, "message")))))
                .bodyToMono(ContactResponse.class)
                .map(ContactResponse::getId)
                .block();
    }

    public ContactResponse findById(String id) {
        return hubSpotClient.get()
                .uri(HubSpotURI.CONTACTS.getPath() + "/" + id)
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> response.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(
                                        new WebApplicationException(
                                                response.statusCode(), ErrorMessage.read(errorBody, "message", "Nenhum registro encontrado")))))
                .bodyToMono(ContactResponse.class)
                .block();
    }
}
