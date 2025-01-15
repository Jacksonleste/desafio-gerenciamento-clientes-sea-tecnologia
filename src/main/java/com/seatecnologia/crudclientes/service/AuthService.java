package com.seatecnologia.crudclientes.service;

import com.seatecnologia.crudclientes.jpa.UsuarioJPA;
import com.seatecnologia.crudclientes.model.Usuario;
import com.seatecnologia.crudclientes.model.dto.ErrorResponse;
import com.seatecnologia.crudclientes.util.JwtTokenUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;

@Service
public class AuthService {

    @Autowired
    private UsuarioJPA jpa;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<?> login(String username, String senha, HttpServletResponse response){
        Usuario usuario = jpa.findByUsername(username);

        if(
            usuario != null &&
            passwordEncoder.matches(senha, usuario.getSenha())
        ){
            setCookie(username, usuario.getPapel().getDescricao(), response);
            return ResponseEntity.ok("Logado");
        }

        ErrorResponse errorResponse = new ErrorResponse("Unauthorized", "Credenciais inválidas. Tente novamente");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(errorResponse);
    }

    public ResponseEntity<?> logout(HttpServletResponse response){
        Cookie cookie = new Cookie("jwt", "");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseEntity.status(HttpStatus.OK).body("Usuário deslogado com sucesso.");
    };

    private void setCookie(String username, String papel, HttpServletResponse response){
        String token = JwtTokenUtil.generateToken(username, papel);
        Cookie jwtCookie = new Cookie("jwt", token);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(24 * 60 * 60);
        response.addCookie(jwtCookie);
    }
}
