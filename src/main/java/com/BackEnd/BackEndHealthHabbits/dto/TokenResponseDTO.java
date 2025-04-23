package com.BackEnd.BackEndHealthHabbits.dto;

import lombok.Builder;

@Builder
public record TokenResponseDTO(String token, String refreshToken) {
}

