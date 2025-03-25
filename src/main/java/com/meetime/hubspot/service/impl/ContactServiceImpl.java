package com.meetime.hubspot.service.impl;

import com.meetime.hubspot.dto.Contact;
import com.meetime.hubspot.integration.HubSpotContactService;
import com.meetime.hubspot.service.ContactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContactServiceImpl implements ContactService {

    private final HubSpotContactService hubSpotContactService;

    @Override
    public Map list() {
        return hubSpotContactService.listAll();
    }

    @Override
    public String create(Contact contact) {
        return hubSpotContactService.save(contact);
    }

    @Override
    public Map get(String id) {
        return hubSpotContactService.findById(id);
    }

}