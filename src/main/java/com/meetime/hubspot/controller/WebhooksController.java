package com.meetime.hubspot.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/webhooks")
@Slf4j
public class WebhooksController {

    @PostMapping("/contacts")
    public ResponseEntity handleWebhookContacts(@RequestBody List<Map<String, Object>> payload) {
        payload.stream().forEach(event -> {
            log.info("Webhook { Event Id: '{}', Type: '{}', recebido para o Object Id: '{}' }", event.get("eventId"), event.get("subscriptionType"), event.get("objectId"));
        });
        return ResponseEntity.status(HttpStatus.OK).body(payload);
    }

}