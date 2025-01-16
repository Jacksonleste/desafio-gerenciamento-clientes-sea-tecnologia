package com.seatecnologia.crudclientes.controller;

import com.seatecnologia.crudclientes.model.Cliente;
import com.seatecnologia.crudclientes.model.Telefone;
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

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editar(
            @RequestBody ClienteCadastroDTO clienteDto,
            @PathVariable("id") Long id
    ){
//        Cliente clienteAtualizado = service.dtoToCliente(clienteDto);
        return service.editarCliente(id, clienteDto);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletar(@PathVariable("id") Long id){
        return service.deleteCliente(id);
    }

    @PostMapping("/{clienteId}/adicionar-telefone")
    public ResponseEntity<?> adicionarTelefone(@PathVariable Long clienteId, @RequestBody ClienteCadastroDTO.TelefoneDTO telefoneDto) {
        return service.adicionarTelefone(clienteId, telefoneDto);
    }

    @DeleteMapping("telefone/deletar/{id}")
    public ResponseEntity<?> deletarTelefone(@PathVariable("id") Long id){
        return service.deleteCliente(id);
    }

}
