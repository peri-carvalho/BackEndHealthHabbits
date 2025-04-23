package com.BackEnd.BackEndHealthHabbits.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationDTO(
        @NotBlank(message = "o campo nome não pode estar vazio")
        String username,
        @NotBlank(message = "o campo de senha não pode estar vazio")
        String password
) {}
