package com.seatecnologia.crudclientes.jpa;

import com.seatecnologia.crudclientes.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioJPA extends JpaRepository<Usuario, Long> {

    Usuario findByUsername(String username);
}
