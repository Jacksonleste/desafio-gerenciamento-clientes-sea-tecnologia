package com.seatecnologia.crudclientes.config;

import com.seatecnologia.crudclientes.enums.Papeis;
import com.seatecnologia.crudclientes.jpa.UsuarioJPA;
import com.seatecnologia.crudclientes.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioJPA usuarioJpa;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (usuarioJpa.findByUsername("admin") == null) {
            Usuario admin = new Usuario();
            admin.setUsername("admin");
            admin.setSenha(passwordEncoder.encode("123qwe!@#"));
            admin.setPapel(Papeis.ADMIN);
            usuarioJpa.save(admin);
            System.out.println("Usuário Admin criado com sucesso!");
        }

        if (usuarioJpa.findByUsername("padrao") == null) {
            Usuario user = new Usuario();
            user.setUsername("padrao");
            user.setSenha(passwordEncoder.encode("123qwe123"));
            user.setPapel(Papeis.USER);
            usuarioJpa.save(user);
            System.out.println("Usuário Padrão criado com sucesso!");
        }
    }
}
