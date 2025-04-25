package com.BackEnd.BackEndHealthHabbits.controllers;

import com.BackEnd.BackEndHealthHabbits.dto.*;
import com.BackEnd.BackEndHealthHabbits.infra.exceptions.UnauthorizedException;
import com.BackEnd.BackEndHealthHabbits.repositories.UserRepository;
import com.BackEnd.BackEndHealthHabbits.services.TokenService;
import com.BackEnd.BackEndHealthHabbits.services.UserAccountService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userAccountRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserAccountService userAccountService;


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody AuthenticationDTO data) {
        var user = userAccountRepository.findByEmail(data.email())
                .orElseThrow(() -> new UnauthorizedException("Usuário não encontrado", HttpStatus.UNAUTHORIZED));

        try {
            var emailPassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
            authenticationManager.authenticate(emailPassword);

            TokenResponseDTO token = tokenService.obterToken(data);

            LoginResponseDTO loginResponse = new LoginResponseDTO(token);

            return ResponseEntity.ok(loginResponse);
        } catch (AuthenticationException ex) {
            throw new UnauthorizedException("Usuário ou senha inválida!");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody @Valid UserDTO userDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userAccountService.createUser(userDTO));
    }

    @Operation(summary = "Atualizar token", description = "Endpoint para atualizar o token de acesso.",
            security = {})
    @PostMapping("/refresh-token")
    public ResponseEntity<TokenResponseDTO> AuthRefreshToken(@RequestBody RequestRefreshDTO requestRefreshDTO){
        return ResponseEntity.ok(tokenService.obterRefreshToken(requestRefreshDTO.refreshToken()));
    }

}
