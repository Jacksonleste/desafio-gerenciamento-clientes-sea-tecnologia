package com.seatecnologia.crudclientes.service;

import com.seatecnologia.crudclientes.jpa.ClienteJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    PageableService pageable;

    @Autowired
    ClienteJPA jpa;

    public ResponseEntity<?> listarClientes(Integer page, Boolean asc, String sortBy){
        PageRequest pageRequest = pageable.getPageable(page, asc, sortBy);
        return ResponseEntity.ok(jpa.findAll(pageRequest));
    }
}
