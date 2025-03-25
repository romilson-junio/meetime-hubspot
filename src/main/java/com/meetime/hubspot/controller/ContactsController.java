package com.meetime.hubspot.controller;

import com.meetime.hubspot.dto.Contact;
import com.meetime.hubspot.service.ContactService;
import com.meetime.hubspot.utils.URLUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contacts")
@Slf4j
public class ContactsController {

    private final ContactService hubSpotService;

    @GetMapping
    public Mono<ResponseEntity> list() {
        return Mono.just(ResponseEntity.ok(hubSpotService.list()));
    }

    @PostMapping
    public Mono<ResponseEntity> create(@Valid @RequestBody Contact contact, HttpServletRequest request) {
        URI location = URLUtils.created(request, hubSpotService.create(contact));
        return Mono.just(ResponseEntity.created(location).build());
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity> get(@PathVariable("id") String id) {
        return Mono.just(ResponseEntity.ok(hubSpotService.get(id)));
    }

}