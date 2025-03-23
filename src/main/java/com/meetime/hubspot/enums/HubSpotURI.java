package com.meetime.hubspot.enums;

import lombok.Getter;

public enum HubSpotURI {
    CONTACTS("/crm/v3/objects/contacts");

    @Getter
    private final String path;

    HubSpotURI(String path) {
        this.path = path;
    }
}
