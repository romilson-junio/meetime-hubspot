package com.meetime.hubspot.service;

import com.meetime.hubspot.dto.Contact;

import java.util.Map;

public interface ContactService {
    Map list();
    String create(Contact contact);
    Map get(String id);
}
