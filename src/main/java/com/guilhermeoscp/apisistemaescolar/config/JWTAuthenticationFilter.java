package com.guilhermeoscp.apisistemaescolar.config;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guilhermeoscp.apisistemaescolar.model.User;
import static com.guilhermeoscp.apisistemaescolar.config.SecurityConstants.*;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

		//JWT = Json Web Token
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private AuthenticationManager authenticationManager;
	
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	
    @Override
    public Authentication attemptAuthentication (HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		try {
    	User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
    	return this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}		
    }

    @Override
    protected void successfulAuthentication (HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
    	String username = ((org.springframework.security.core.userdetails.User) authResult.getPrincipal()).getUsername();
    	String token = Jwts
    			.builder().setSubject(username)
    			.setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
    			.signWith(SignatureAlgorithm.HS512, SECRET)
    			.compact();
    	String bearerToken = TOKEN_PREFIX+ token;
    	response.getWriter().write(bearerToken);
    	response.addHeader(HEADER_STRING, bearerToken);
    }
}
