package com.meetime.hubspot.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.meetime.hubspot.dto.Contatc;
import reactor.core.publisher.Mono;

public interface ContactService {
    Mono<JsonNode> list();
    Mono<JsonNode> create(Contatc contatc);
}
