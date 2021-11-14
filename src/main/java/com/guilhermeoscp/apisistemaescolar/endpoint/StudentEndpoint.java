package com.guilhermeoscp.apisistemaescolar.endpoint;

import java.util.List;
import java.util.Arrays;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.guilhermeoscp.apisistemaescolar.model.Student;

@RestController
@RequestMapping("student")
public class StudentEndpoint {
	
	@RequestMapping(method = RequestMethod.GET,path = "/list")
	public List<Student> listAll () {
		return Arrays.asList(new Student("Jonathan"), new Student("Joseph"));
	}
}
