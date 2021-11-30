package com.guilhermeoscp.apisistemaescolar.javaclient;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.guilhermeoscp.apisistemaescolar.model.PageableResponse;
import com.guilhermeoscp.apisistemaescolar.model.Student;

public class JavaSpringClientTest {
	
	public static void main(String[] args) {
		RestTemplate restTemplate = new RestTemplateBuilder()
				.rootUri("http://localhost:8080/v1/protected/students")
				.basicAuthentication("admin", "St@ndM@st3r").build();
		Student student = restTemplate.getForObject("/{id}", Student.class, 1);
		System.out.println(student);
		System.out.println("--------------------");
		
		ResponseEntity<Student> forEntity = restTemplate.getForEntity("/{id}", Student.class, 1);
		System.out.println(forEntity.getBody());
		System.out.println("--------------------");
		
/*		Student[] students = restTemplate.getForObject("/", Student[].class);
		System.out.println(Arrays.toString(students));
		System.out.println("--------------------"); 
		
		ResponseEntity<List<Student>> exchange = restTemplate.exchange("/", HttpMethod.GET,null, 
					new ParameterizedTypeReference<List<Student>>() {});
		System.out.println(exchange.getBody()); */
		
		ResponseEntity<PageableResponse<Student>> exchange = restTemplate.exchange("/?sort=id,desc&sort=name,asc", HttpMethod.GET,null, 
				new ParameterizedTypeReference<PageableResponse<Student>>() {});
		System.out.println(exchange);
	}
}
