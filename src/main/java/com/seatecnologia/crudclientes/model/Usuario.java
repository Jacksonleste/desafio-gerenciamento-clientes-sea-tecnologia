package com.seatecnologia.crudclientes.model;

import com.seatecnologia.crudclientes.enums.Papeis;
import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "login")
    private String login;

    @Column(name = "papel")
    private Papeis papel;
}
