package com.BackEnd.BackEndHealthHabbits.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationDTO(
        @NotBlank(message = "o campo email não pode estar vazio")
        String email,
        @NotBlank(message = "o campo de senha não pode estar vazio")
        String password
) {}
