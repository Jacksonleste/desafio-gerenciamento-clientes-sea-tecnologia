package com.seatecnologia.crudclientes.jpa;

import com.seatecnologia.crudclientes.model.Cliente;
import com.seatecnologia.crudclientes.model.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmailJPA extends JpaRepository<Email, Long> {
}
