package com.BackEnd.BackEndHealthHabbits.dto;

import lombok.Data;

@Data
public class LoginResponseDTO {

    private TokenResponseDTO tokenResponse;
    private boolean firstLogin;

    public LoginResponseDTO() {
    }

    // Construtor que recebe TokenResponseDTO + boolean
    public LoginResponseDTO(TokenResponseDTO tokenResponse, boolean firstLogin) {
        this.tokenResponse = tokenResponse;
        this.firstLogin = firstLogin;
    }
}
