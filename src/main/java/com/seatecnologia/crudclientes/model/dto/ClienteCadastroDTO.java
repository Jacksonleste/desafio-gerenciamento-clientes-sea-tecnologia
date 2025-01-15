package com.seatecnologia.crudclientes.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ClienteCadastroDTO {

    private String nome;
    private String cpf;
    private String email;
    private EnderecoDTO endereco;
    private List<TelefoneDTO> telefones;

    @Getter
    @Setter
    public static class EnderecoDTO {
        private String cep;
        private String logradouro;
        private String bairro;
        private String cidade;
        private String uf;
        private String complemento;
    }

    @Getter
    @Setter
    public static class TelefoneDTO {
        private String numero;
        private String tipo;
    }
}
