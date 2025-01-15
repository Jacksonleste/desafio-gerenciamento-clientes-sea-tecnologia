package com.seatecnologia.crudclientes.controller;

import com.fasterxml.jackson.annotation.JsonValue;
import com.seatecnologia.crudclientes.enums.Papeis;
import com.seatecnologia.crudclientes.jpa.UsuarioJPA;
import com.seatecnologia.crudclientes.model.Usuario;
import com.seatecnologia.crudclientes.model.dto.loginDTO;
import com.seatecnologia.crudclientes.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService service;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody loginDTO loginRequest, HttpServletResponse response){
        return service.login(loginRequest.getUsuario(), loginRequest.getSenha(), response);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response){
        return service.logout(response);
    }
}