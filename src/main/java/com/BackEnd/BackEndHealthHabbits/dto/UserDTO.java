package com.BackEnd.BackEndHealthHabbits.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UserDTO(
        @NotBlank(message = "o campo nome não pode estar vazio")
        @Email String email,
        String name,
        @Pattern(regexp = "^(?=.*[!@#$%^&*()_+{}\\[\\]:;\"'<>?,./\\\\|-])[A-Za-z\\d!@#$%^&*()_+{}\\[\\]:;\"'<>?,./\\\\|-]{1,8}$") String password,
        @NotNull(message = "Perfil não pode ser nulo")
        Long profileId) {}
