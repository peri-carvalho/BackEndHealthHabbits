package com.BackEnd.BackEndHealthHabbits.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserDTO(
        @NotBlank(message = "o campo nome não pode estar vazio")
        String username,
        String password,
        @NotNull(message = "Perfil não pode ser nulo")
        Long profileId) {}
