package com.seatecnologia.crudclientes.model;

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

    @Column(name = "numero", unique = true, nullable = false, length = 15)
    private String numero;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_telefone", nullable = false)
    private TipoTelefone tipoTelefone;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
}
