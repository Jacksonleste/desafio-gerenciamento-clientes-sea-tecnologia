package com.seatecnologia.crudclientes.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seatecnologia.crudclientes.model.dto.ErrorResponse;
import com.seatecnologia.crudclientes.util.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String COOKIE_NAME = "jwt"; // Nome do cookie onde o JWT é armazenado
    private final JwtTokenUtil jwtTokenUtil;


    public JwtAuthenticationFilter(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (request.getRequestURI().equals("/api/auth/login")) {
            filterChain.doFilter(request, response); // Ignora o filtro para o login
            return;
        }

        // Recupera o token JWT do cookie
        String token = getTokenFromCookie(request);

        if (token != null && jwtTokenUtil.isTokenValid(token)) {
            // Se o token for válido, extrai as claims do token
            Claims claims = jwtTokenUtil.getClaimsFromToken(token);
            String username = jwtTokenUtil.getUsernameFromToken(token);
            String papel = claims.get("papel", String.class);


            // Cria um objeto de autenticação com o nome de usuário e o contexto de segurança
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    List.of(new SimpleGrantedAuthority(papel)));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {

            if (request.getRequestURI().equals("/api/auth/logout")) {
                ErrorResponse errorResponse = new ErrorResponse("Bad Request", "Usuário já está deslogado.");
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setContentType("application/json;charset=UTF-8");

                ObjectMapper objectMapper = new ObjectMapper();
                String jsonResponse = objectMapper.writeValueAsString(errorResponse);
                response.getWriter().write(jsonResponse);
                return;
            }

            ErrorResponse errorResponse = new ErrorResponse("Unauthorized", "Token não fornecido ou inválido.");

            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json;charset=UTF-8");

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(errorResponse);
            response.getWriter().write(jsonResponse);
            return;
        }

        // Continua o processamento da requisição
        filterChain.doFilter(request, response);
    }

    // Método para recuperar o JWT do cookie
    private String getTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (COOKIE_NAME.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
