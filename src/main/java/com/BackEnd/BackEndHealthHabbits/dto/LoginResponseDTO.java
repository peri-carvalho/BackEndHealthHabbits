package com.BackEnd.BackEndHealthHabbits.dto;

import lombok.Data;

@Data
public class LoginResponseDTO {

    private TokenResponseDTO tokenResponse;

    public LoginResponseDTO() {
    }

    // Construtor que recebe TokenResponseDTO + boolean
    public LoginResponseDTO(TokenResponseDTO tokenResponse) {
        this.tokenResponse = tokenResponse;
    }
}
