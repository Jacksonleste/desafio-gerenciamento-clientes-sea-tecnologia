package com.seatecnologia.crudclientes.jpa;

import com.seatecnologia.crudclientes.enums.TipoTelefone;
import com.seatecnologia.crudclientes.model.Cliente;
import com.seatecnologia.crudclientes.model.Telefone;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.data.repository.query.Param;

import java.util.function.Function;

public interface TelefoneJPA extends JpaRepository<Telefone, Long> {
}
