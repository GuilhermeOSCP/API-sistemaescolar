package com.guilhermeoscp.apisistemaescolar.config;

// import java.util.concurrent.TimeUnit;

public class SecurityConstants {
	
	// Authorization Bearer
	static final String SECRET = "StandProud";
	static final String TOKEN_PREFIX = "Bearer ";
	static final String HEADER_STRING = "Authorization";
	static final String SIGN_UP_URL = "/users/sign-up";
	static final long EXPIRATION_TIME = 86400000l;

	/*
	public static void main (String[] args) {
		System.out.println(TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));
	}
	*/
	
}
