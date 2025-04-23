package com.BackEnd.BackEndHealthHabbits.infra.security;


import com.BackEnd.BackEndHealthHabbits.entities.User;
import com.BackEnd.BackEndHealthHabbits.entities.enums.Rule;
import com.BackEnd.BackEndHealthHabbits.services.TokenService;
import com.auth0.jwt.JWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    // Defina as rotas que não precisam de autenticação (se necessário)
    private static final List<String> EXCLUDED_PATHS = Arrays.asList(
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/favicon.ico"
    );

    public JwtAuthenticationFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws ServletException, IOException {

        String requestPath = request.getRequestURI();

        // Pula o filtro em rotas excluídas
        if (isExcludedPath(requestPath)) {
            chain.doFilter(request, response);
            return;
        }

        String token = recoverToken(request);
        if (token != null) {
            String name = tokenService.validateToken(token);
            if (name == null) {
                // Token inválido ou expirado
                SecurityContextHolder.clearContext();
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"Token inválido ou expirado.\"}");
                response.getWriter().flush();
                return;
            }

            var decodedJWT = JWT.decode(token);
            String rolesString = decodedJWT.getClaim("roles").asString();
            String profileName = decodedJWT.getClaim("profile").asString();

            // Converte a string de roles em um Set de `Rule`
            Set<Rule> userRules = Optional.ofNullable(rolesString)
                    .map(r -> Arrays.stream(r.split(","))
                            .map(String::trim)
                            .map(String::toUpperCase)
                            .map(Rule::valueOf)
                            .collect(Collectors.toSet()))
                    .orElse(Collections.emptySet());

            // Expande as permissões se você estiver usando hierarquia
            Set<Rule> expandedRules = PermissionUtils.expandRules(userRules);

            // Converte as regras para GrantedAuthority
            // OBS: Use List<GrantedAuthority> em vez de var
            List<GrantedAuthority> expandedAuthorities = expandedRules.stream()
                    .map(rule -> new org.springframework.security.core.authority.SimpleGrantedAuthority(rule.name()))
                    .collect(Collectors.toList());

            // Monta o objeto de usuário para injetar no Spring Security Context
            User user = new User();
            user.setName(name);
            user.setProfileName(profileName);
            user.setAuthorities(expandedAuthorities);

            var authentication = new UsernamePasswordAuthenticationToken(
                    user, null, expandedAuthorities
            );
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }


    private String recoverToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        return (authHeader != null && authHeader.startsWith("Bearer "))
                ? authHeader.substring(7)
                : null;
    }

    private boolean isExcludedPath(String requestPath) {
        return EXCLUDED_PATHS.stream().anyMatch(requestPath::startsWith);
    }
}
