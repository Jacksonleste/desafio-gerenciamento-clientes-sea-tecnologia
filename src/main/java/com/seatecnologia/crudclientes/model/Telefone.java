package com.seatecnologia.crudclientes.model;

import jakarta.persistence.*;

@Entity
@Table(name = "telefones")
public class Telefone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "numero")
    private String telefone;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
}
