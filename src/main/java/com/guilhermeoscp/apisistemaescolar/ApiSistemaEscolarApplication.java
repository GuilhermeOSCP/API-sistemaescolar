package com.guilhermeoscp.apisistemaescolar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan(basePackages = "com.guilhermeoscp.apisistemaescolar.endpoint")
@SpringBootApplication
public class ApiSistemaEscolarApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiSistemaEscolarApplication.class, args);
	}

}
