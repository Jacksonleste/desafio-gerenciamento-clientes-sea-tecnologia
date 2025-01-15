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
    @Column(name = "id")
    private Long id;

    @Column(name = "numero", unique = true)
    private String numero;

    @Column(name = "tipo_telefone")
    @Enumerated(EnumType.STRING)
    private TipoTelefone tipoTelefone;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
}
