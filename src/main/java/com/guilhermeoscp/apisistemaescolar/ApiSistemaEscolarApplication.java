package com.guilhermeoscp.apisistemaescolar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication //Inclui as Anotações @Configuration @EnableAutoConfiguration @ComponentScan
public class ApiSistemaEscolarApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiSistemaEscolarApplication.class, args);
	}

}
