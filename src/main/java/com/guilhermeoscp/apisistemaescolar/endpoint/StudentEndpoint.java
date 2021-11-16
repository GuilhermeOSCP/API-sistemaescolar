package com.guilhermeoscp.apisistemaescolar.endpoint;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.guilhermeoscp.apisistemaescolar.error.CustomErrorType;
import com.guilhermeoscp.apisistemaescolar.model.Student;
import com.guilhermeoscp.apisistemaescolar.util.DateUtil;

@RestController
@RequestMapping("students")
public class StudentEndpoint {
	
	@Autowired
	private DateUtil dateUtil;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> listAll () {
		System.out.println(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
		return new ResponseEntity<>(Student.studentList, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
	public ResponseEntity<?> getStudentById (@PathVariable("id") int id) {
		Student student = new Student();
		student.setId(id);
		int index = Student.studentList.indexOf(student);
		if(index == -1) {
			return new ResponseEntity<>(new CustomErrorType("Student not found"), HttpStatus.NOT_FOUND);
		}
		else {
			return new ResponseEntity<>(Student.studentList.get(index), HttpStatus.OK);
		}
	}
}
