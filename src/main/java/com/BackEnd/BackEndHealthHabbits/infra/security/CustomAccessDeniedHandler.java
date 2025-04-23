package com.BackEnd.BackEndHealthHabbits.infra.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException ex) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.getWriter().write("{\"timestamp\": \"" + Instant.now() + "\","
                + "\"status\": 403,"
                + "\"error\": \"Acesso negado\","
                + "\"message\": \"Você não tem permissão para acessar este recurso.\","
                + "\"path\": \"" + request.getRequestURI() + "\"}");
    }
}
