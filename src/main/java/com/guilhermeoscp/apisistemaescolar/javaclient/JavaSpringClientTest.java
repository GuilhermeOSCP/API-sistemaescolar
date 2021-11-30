package com.guilhermeoscp.apisistemaescolar.javaclient;

import java.util.List;

import com.guilhermeoscp.apisistemaescolar.model.Student;

public class JavaSpringClientTest {
	
	public static void main(String[] args) {
	
		Student studentPost = new Student();
		studentPost.setName("Speedwagon");
		studentPost.setEmail("speedwagon@gmail.com");
		JavaClientDAO dao = new JavaClientDAO();
// 		System.out.println(dao.findById(1));
		List<Student> students = dao.listAll();
		System.out.println(students);
//		System.out.println(dao.save(studentPost));
	}
	
}
