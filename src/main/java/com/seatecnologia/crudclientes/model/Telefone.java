package com.seatecnologia.crudclientes.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.seatecnologia.crudclientes.enums.TipoTelefone;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "telefones")
public class Telefone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "numero", nullable = false, length = 15)
    private String numero;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_telefone", nullable = false)
    private TipoTelefone tipoTelefone;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    @JsonBackReference
    private Cliente cliente;
}
