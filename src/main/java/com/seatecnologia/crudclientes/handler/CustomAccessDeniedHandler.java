package com.seatecnologia.crudclientes.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN); // HTTP 403
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"error\": \"Access Denied\", \"message\": \"Você não tem permissão para acessar este recurso.\"}");
    }
}
