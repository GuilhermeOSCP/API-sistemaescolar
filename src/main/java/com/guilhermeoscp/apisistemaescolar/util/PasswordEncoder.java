package com.guilhermeoscp.apisistemaescolar.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// Coloque o valor que você deseja em "Mudar@123" e execute esta classe para gerar a senha criptografada
public class PasswordEncoder {
	public static void main(String[] args) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		System.out.println(passwordEncoder.encode("$uPguilherme.97@.*"));
	}
}
