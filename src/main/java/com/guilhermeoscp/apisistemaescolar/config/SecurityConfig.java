package com.guilhermeoscp.apisistemaescolar.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

// As linhas que estão comentadas são para obter os usuários através do MySQL
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
 

//Inserção automática dos usuários pelo MySQL, se for usar o Mysql descomente da linha 20 até a 38.
	//	@Autowired
	//	private CustomUserDetailService customUserDetailService;
	//	
	//	@Override
	//	protected void configure(HttpSecurity http) throws Exception {		
	//		http.cors().and().csrf().disable().authorizeRequests()
	//		.antMatchers(HttpMethod.GET, SIGN_UP_URL).permitAll()
	//		.antMatchers("/*/protected/**").hasRole("USER")
	//		.antMatchers("/*/admin/**").hasRole("ADMIN")
	//		.and()
	//		.addFilter(new JWTAuthenticationFilter(authenticationManager()))
	//		.addFilter(new JWTAuthorizationFilter(authenticationManager(), customUserDetailService));
	//	}
	
	
	//	@Override
	//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	//		auth.userDetailsService(customUserDetailService).passwordEncoder(new BCryptPasswordEncoder());
	//	}

	
//Inserção manual dos usuários pelo software invés do MySQL, se for usar o Mysql comente tudo abaixo deste comentario
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/*/protected/**").hasRole("USER")
				.antMatchers("/*/admin/**").hasRole("ADMIN")
				.and()
				.httpBasic()
				.and()
				.csrf().disable();
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		auth.inMemoryAuthentication()
		.withUser("guilherme.pimentel").password(encoder.encode("St@rPlat1num")).roles("USER")
		.and()
		.withUser("admin").password(encoder.encode("St@ndM@st3r")).roles("USER","ADMIN");
	}
}
