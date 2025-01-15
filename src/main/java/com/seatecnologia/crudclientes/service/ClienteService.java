package com.seatecnologia.crudclientes.service;

import com.seatecnologia.crudclientes.enums.TipoTelefone;
import com.seatecnologia.crudclientes.jpa.ClienteJPA;
import com.seatecnologia.crudclientes.jpa.EnderecoJPA;
import com.seatecnologia.crudclientes.model.Cliente;
import com.seatecnologia.crudclientes.model.Email;
import com.seatecnologia.crudclientes.model.Endereco;
import com.seatecnologia.crudclientes.model.Telefone;
import com.seatecnologia.crudclientes.model.dto.ClienteCadastroDTO;
import com.seatecnologia.crudclientes.model.dto.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    PageableService pageable;

    @Autowired
    ClienteJPA clienteJPA;

    @Autowired
    EnderecoJPA enderecoJPA;

    public ResponseEntity<?> listarClientes(Integer page, Boolean asc, String sortBy){
        PageRequest pageRequest = pageable.getPageable(page, asc, sortBy);
        return ResponseEntity.ok(clienteJPA.findAll(pageRequest));
    }

    @Transactional
    public ResponseEntity<?> cadastrarClientes(ClienteCadastroDTO clienteDto){

        Cliente cliente = new Cliente();
        cliente.setNome(clienteDto.getNome());
        cliente.setCpf(clienteDto.getCpf());

        // Validando se tem pelo menos um email
        if (clienteDto.getEmails() == null || clienteDto.getEmails().isEmpty()) {
            ErrorResponse errorResponse = new ErrorResponse("Bad Request", "O cliente deve ter pelo menos um email.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        // Mapeando Email
        List<Email> emails = clienteDto.getEmails().stream()
                .map(dto -> {
                    Email email = new Email();
                    email.setEmail(dto);
                    email.setCliente(cliente);
                    return email;
                })
                .collect(Collectors.toList());
        cliente.setEmails(emails);

        // Mapeando o endereço
        Endereco endereco = new Endereco();
        endereco.setCep(clienteDto.getEndereco().getCep());
        endereco.setLogradouro(clienteDto.getEndereco().getLogradouro());
        endereco.setBairro(clienteDto.getEndereco().getBairro());
        endereco.setCidade(clienteDto.getEndereco().getCidade());
        endereco.setUf(clienteDto.getEndereco().getUf());
        endereco.setComplemento(clienteDto.getEndereco().getComplemento());
        cliente.setEndereco(endereco);

        Endereco enderecoExistente = enderecoJPA.findByCepAndLogradouroAndBairroAndCidadeAndUfAndComplemento(
                endereco.getCep(),
                endereco.getLogradouro(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getUf(),
                endereco.getComplemento()
        );

        if (enderecoExistente != null) {
            cliente.setEndereco(enderecoExistente);
        } else {
            cliente.setEndereco(endereco);
        }

        // Validando se tem pelo menos um telefone
        if (cliente.getTelefones() == null || cliente.getTelefones().isEmpty()) {
            ErrorResponse errorResponse = new ErrorResponse("Bad Request", "O cliente deve ter pelo menos um telefone.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        // Mapeando os telefones
        List<Telefone> telefones = clienteDto.getTelefones().stream()
                .map(dto -> {
                    Telefone telefone = new Telefone();
                    telefone.setNumero(dto.getNumero());
                    telefone.setTipoTelefone(TipoTelefone.getEnumByDescricao(dto.getTipo()));
                    telefone.setCliente(cliente);
                    return telefone;
                })
                .collect(Collectors.toList());
        cliente.setTelefones(telefones);

        // Salvar no banco (exemplo com serviço)
        clienteJPA.saveAndFlush(cliente);

        return ResponseEntity.status(HttpStatus.CREATED).body("Cliente Cadastrado com Sucesso.");
    }
}
