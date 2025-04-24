package com.BackEnd.BackEndHealthHabbits.services;

import com.BackEnd.BackEndHealthHabbits.dto.AuthenticationDTO;
import com.BackEnd.BackEndHealthHabbits.dto.TokenResponseDTO;
import com.BackEnd.BackEndHealthHabbits.entities.User;
import com.BackEnd.BackEndHealthHabbits.infra.exceptions.UnauthorizedException;
import com.BackEnd.BackEndHealthHabbits.repositories.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.stream.Collectors;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    @Value("${auth.jwt.token.expiration}")
    private Integer tokenExpirationHours;

    @Value("${auth.jwt.refresh-token.expiration}")
    private Integer refreshTokenExpirationHours;

    @Autowired
    private UserRepository userRepository;

    public TokenResponseDTO obterToken(@Valid AuthenticationDTO authenticationDTO) {
        User user = userRepository.findByEmail(authenticationDTO.email())
                .orElseThrow(() -> new UnauthorizedException("User not found", HttpStatus.UNAUTHORIZED));

        return TokenResponseDTO.builder()
                .token(generateToken(user, tokenExpirationHours))
                .refreshToken(generateToken(user, refreshTokenExpirationHours))
                .build();
    }

    private String generateToken(User user, Integer expirationHours) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String roles = user.getProfile().getRule().stream()
                    .map(Enum::name)
                    .collect(Collectors.joining(","));

            // Iniciando a construção do token
            JWTCreator.Builder jwtBuilder = JWT.create()
                    .withIssuer("auth-pjapi")
                    .withSubject(user.getUsername())
                    .withClaim("profile", user.getProfile().getName())
                    .withClaim("roles", roles)
                    .withClaim("name",user.getName())
                    .withExpiresAt(generateExpirationDate(expirationHours));

            // Assinando o token
            String token = jwtBuilder.sign(algorithm);

            System.out.println("Token gerado: " + token);  // Log do token gerado
            return token;
        } catch (JWTCreationException e) {
            throw new RuntimeException("Erro ao gerar o token: " + e.getMessage());
        }
    }


    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            var verifier = JWT.require(algorithm)
                    .withIssuer("auth-pjapi")
                    .build();
            var decodedJWT = verifier.verify(token);

            String subject = decodedJWT.getSubject();
            String profile = decodedJWT.getClaim("profile").asString();
            String roles = decodedJWT.getClaim("roles").asString();
            String name = decodedJWT.getClaim("name").asString();

            System.out.println("Token validado para CPF: " + subject);
            System.out.println("Perfil: " + profile);
            System.out.println("Regras: " + roles);

            return subject;

        } catch (TokenExpiredException e) {
            return null;
        } catch (JWTVerificationException e) {
            return null;
        }
    }

    private Instant generateExpirationDate(Integer expirationHours) {
        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
        return now.plusHours(expirationHours).toInstant();
    }

    public TokenResponseDTO obterRefreshToken(String refreshToken) {
        String email = validateToken(refreshToken);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UnauthorizedException("User not found", HttpStatus.UNAUTHORIZED));

        return TokenResponseDTO.builder()
                .token(generateToken(user, tokenExpirationHours))
                .refreshToken(generateToken(user, refreshTokenExpirationHours))
                .build();
    }
}
