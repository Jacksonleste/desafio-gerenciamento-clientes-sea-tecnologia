package com.seatecnologia.crudclientes.jpa;

import com.seatecnologia.crudclientes.model.Cliente;
import com.seatecnologia.crudclientes.model.Usuario;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteJPA extends JpaRepository<Cliente, Long> {

    @Override
    Page<Cliente> findAll(Pageable pageable);

    @Override
    <S extends Cliente> S saveAndFlush(S entity);
}
