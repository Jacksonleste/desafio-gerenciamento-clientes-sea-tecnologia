package com.seatecnologia.crudclientes.controller;

import com.seatecnologia.crudclientes.model.dto.ClienteCadastroDTO;
import com.seatecnologia.crudclientes.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    ClienteService service;

    @GetMapping("/listar")
    public ResponseEntity<?> listar(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Boolean asc,
            @RequestParam(required = false) String sortBy
    ){
        return service.listarClientes(page, asc, sortBy);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@RequestBody ClienteCadastroDTO clienteDto){
        return service.cadastrarClientes(clienteDto);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletar(@PathVariable("id") Long id){
        return service.deleteCliente(id);
    }
}
