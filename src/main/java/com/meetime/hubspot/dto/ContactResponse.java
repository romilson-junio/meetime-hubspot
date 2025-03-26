package com.meetime.hubspot.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.Map;

@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContactResponse {
    private String id;
    private String email;
    private String firstname;
    private String lastname;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    @JsonProperty("properties")
    private void properties(Map<String, String> properties) {
        this.email = properties.get("email");
        this.firstname = properties.get("firstname");
        this.lastname = properties.get("lastname");
    }
}
