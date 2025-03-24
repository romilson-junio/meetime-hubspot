package com.meetime.hubspot.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Map;

public record Contact(
    @NotBlank(message = "Firstname é um campo obrigatório")
    String firstname,
    String lastname,
    @NotBlank(message = "Email é um campo obrigatório")
    @Email(message = "O Email informado é inválido")
    String email
) {
    public Map<String, Object> toData() {
        return Map.of(
                "properties", Map.of(
                        "firstname", firstname(),
                        "lastname", lastname(),
                        "email", email()
                )
        );
    }
}
