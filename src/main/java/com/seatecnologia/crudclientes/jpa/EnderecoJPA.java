package com.seatecnologia.crudclientes.jpa;

import com.seatecnologia.crudclientes.model.Endereco;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EnderecoJPA extends JpaRepository<Endereco, Long> {

    Endereco findByCepAndLogradouroAndBairroAndCidadeAndUf(String cep, String logradouro, String bairro, String cidade, String uf);
}
