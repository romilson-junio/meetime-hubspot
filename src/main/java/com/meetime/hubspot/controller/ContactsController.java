package com.meetime.hubspot.controller;

import com.meetime.hubspot.dto.Contact;
import com.meetime.hubspot.service.ContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contacts")
@Slf4j
public class ContactsController {

    private final ContactService hubSpotService;

    @GetMapping
    public Mono list() {
        return hubSpotService.list();
    }

    @PostMapping
    public Mono create(@Valid @RequestBody Contact contact) {
        return Mono.just(hubSpotService.create(contact));
    }

}