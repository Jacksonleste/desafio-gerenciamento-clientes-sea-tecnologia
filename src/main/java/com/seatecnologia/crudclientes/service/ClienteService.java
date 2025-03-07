package com.seatecnologia.crudclientes.service;

import com.seatecnologia.crudclientes.enums.TipoTelefone;
import com.seatecnologia.crudclientes.jpa.ClienteJPA;
import com.seatecnologia.crudclientes.jpa.EnderecoJPA;
import com.seatecnologia.crudclientes.jpa.TelefoneJPA;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    PageableService pageable;

    @Autowired
    ClienteJPA clienteJPA;

    @Autowired
    EnderecoJPA enderecoJPA;

    @Autowired
    TelefoneJPA telefoneJPA;

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
        if (clienteDto.getTelefones() == null || clienteDto.getTelefones().isEmpty()) {
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

    @Transactional
    public ResponseEntity<?> editarCliente(Long id, ClienteCadastroDTO clienteDto) {
        // Verificar se o cliente existe
        Cliente cliente = clienteJPA.findById(id).orElse(null);
        if (cliente == null) {
            ErrorResponse errorResponse = new ErrorResponse("Not Found", "Cliente não encontrado.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        // Atualizar os dados do cliente
        cliente.setNome(clienteDto.getNome());
        cliente.setCpf(clienteDto.getCpf());

        // Atualizar endereço
        Endereco novoEndereco = new Endereco();
        novoEndereco.setCep(clienteDto.getEndereco().getCep());
        novoEndereco.setLogradouro(clienteDto.getEndereco().getLogradouro());
        novoEndereco.setBairro(clienteDto.getEndereco().getBairro());
        novoEndereco.setCidade(clienteDto.getEndereco().getCidade());
        novoEndereco.setUf(clienteDto.getEndereco().getUf());
        novoEndereco.setComplemento(clienteDto.getEndereco().getComplemento());

        // Verificar se o endereço já existe
        Endereco enderecoExistente = enderecoJPA.findByCepAndLogradouroAndBairroAndCidadeAndUfAndComplemento(
                novoEndereco.getCep(),
                novoEndereco.getLogradouro(),
                novoEndereco.getBairro(),
                novoEndereco.getCidade(),
                novoEndereco.getUf(),
                novoEndereco.getComplemento()
        );

        Endereco enderecoToDelete = null;
        Long mesmoEndereco = clienteJPA.countByEndereco(cliente.getEndereco());

        if (enderecoExistente != null) {
            if (enderecoExistente != cliente.getEndereco() && mesmoEndereco == 1) enderecoToDelete = cliente.getEndereco();
            cliente.setEndereco(enderecoExistente);
        } else {
            if (mesmoEndereco == 1) {
                enderecoToDelete = cliente.getEndereco();
            }
            cliente.setEndereco(novoEndereco);
        }

        // Salvar cliente atualizado
        clienteJPA.saveAndFlush(cliente);

        if(enderecoToDelete != null) enderecoJPA.delete(enderecoToDelete);

        return ResponseEntity.status(HttpStatus.OK).body("Cliente atualizado com sucesso.");
    }

    public ResponseEntity<?> deleteCliente(Long id) {
        try {
            if (!clienteJPA.existsById(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado.");
            }

            clienteJPA.deleteById(id);

            return ResponseEntity.status(HttpStatus.OK).body("Cliente deletado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar o cliente: " + e.getMessage());
        }
    }

    public ResponseEntity<?> adicionarTelefone(Long clienteId, ClienteCadastroDTO.TelefoneDTO telefoneDto) {
        Cliente cliente = clienteJPA.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Telefone telefone = new Telefone();
        telefone.setNumero(telefoneDto.getNumero());
        telefone.setTipoTelefone(TipoTelefone.getEnumByDescricao(telefoneDto.getTipo()));
        telefone.setCliente(cliente);

        return ResponseEntity.status(HttpStatus.OK).body("Telefone adicionado com sucesso.");
    }

    public ResponseEntity<?> removerTelefone(Long id) {
        try {
            if (!telefoneJPA.existsById(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Telefone não encontrado.");
            }

            telefoneJPA.deleteById(id);

            return ResponseEntity.status(HttpStatus.OK).body("Telefone deletado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar o telefone: " + e.getMessage());
        }
    }

}
