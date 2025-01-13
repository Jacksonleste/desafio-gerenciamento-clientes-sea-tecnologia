package com.seatecnologia.crudclientes;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.slf4j.Logger;

@SpringBootApplication
public class CrudClientesApplication {

	private static final Logger logger = LoggerFactory.getLogger(CrudClientesApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CrudClientesApplication.class, args);
		logger.warn("\nO Servidor Spring Boot está rodando!\n");
	}

}
