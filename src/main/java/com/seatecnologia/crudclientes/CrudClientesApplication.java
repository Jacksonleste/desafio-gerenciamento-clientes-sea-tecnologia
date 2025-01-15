package com.seatecnologia.crudclientes;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class CrudClientesApplication {

	private static final Logger logger = LoggerFactory.getLogger(CrudClientesApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CrudClientesApplication.class, args);
		logger.warn("\nO Servidor Spring Boot está rodando!\n");
	}

	@Bean
	public PasswordEncoder getPasswordEncoder(){
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
}
