package com.meetime.hubspot.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.meetime.hubspot.dto.Contact;
import com.meetime.hubspot.service.ContactService;
import com.meetime.hubspot.integration.HubSpotContactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContactServiceImpl implements ContactService {

    private final HubSpotContactService hubSpotContactService;

    @Override
    public Mono<JsonNode> list() {
        return hubSpotContactService.listAll();
    }

    @Override
    public Mono<JsonNode> create(Contact contact) {
        return hubSpotContactService.save(contact);
    }

}