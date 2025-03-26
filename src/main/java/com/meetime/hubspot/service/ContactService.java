package com.meetime.hubspot.service;

import com.meetime.hubspot.dto.Contact;
import com.meetime.hubspot.dto.ContactResponse;

import java.util.List;

public interface ContactService {
    List<ContactResponse> list();
    String create(Contact contact);
    ContactResponse get(String id);
}
