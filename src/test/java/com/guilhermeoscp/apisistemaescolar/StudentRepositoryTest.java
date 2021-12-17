package com.guilhermeoscp.apisistemaescolar;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.guilhermeoscp.apisistemaescolar.model.Student;
import com.guilhermeoscp.apisistemaescolar.repository.StudentRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StudentRepositoryTest {
	
	@Autowired
	private StudentRepository studentRepository;
	
	@SuppressWarnings("deprecation")
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void createShouldPersistData() {
		// Cenario
		Student student = new Student("Caesar Zeppeli", "caesar.zeppeli@gmail.com");
		
		//Procedimento
		this.studentRepository.save(student);
		
		//Teste
		Assertions.assertThat(student.getId()).isNotNull();
		Assertions.assertThat(student.getName()).isEqualTo("Caesar Zeppeli");
		Assertions.assertThat(student.getEmail()).isEqualTo("caesar.zeppeli@gmail.com");
	}
	
	@Test
	public void deleteShouldRemoveData() {
		// Cenario
		Student student = new Student("Caesar Zeppeli", "caesar.zeppeli@gmail.com");
		
		//Procedimento
		this.studentRepository.save(student);
		studentRepository.delete(student);
		
		//Teste
		assertThat(studentRepository.findById(student.getId())).isNull();
	}
	
	@Test
	public void updateShouldChangeAndPersistData() {
		// Cenario
		Student student = new Student("Caesar Zeppeli", "caesar.zeppeli@gmail.com");
		
		//Procedimento
		this.studentRepository.save(student);
		student.setName("William Anthonio Zeppeli");
		student.setEmail("william.zeppeli@gmail.com");
		this.studentRepository.save(student);

		
		//Teste
		Assertions.assertThat(student.getName()).isEqualTo("William Anthonio Zeppeli");
		Assertions.assertThat(student.getEmail()).isEqualTo("william.zeppeli@gmail.com");
	}
	
	@Test
	public void findByNameIgnoreCaseContainingShouldIgnoreCase() {
		// Cenario
		Student student = new Student("Caesar Zeppeli", "caesar.zeppeli@gmail.com");
		Student student2 = new Student("caesar zeppeli", "caesar.zeppeli@gmail.com");
		
		//Procedimento
		this.studentRepository.save(student);
		this.studentRepository.save(student2);
		List<Student> studentList = studentRepository.findByNameIgnoreCaseContaining("caesar");
	
		//Teste
		assertThat(studentList.size()).isEqualTo(2);
	}
	
	@Test
	public void createWhenNameIsNullSholdThrowConstraintViolationException() {
		thrown.expect(ConstraintViolationException.class);
		thrown.expectMessage("O campo nome do estudante é obrigatório");
		this.studentRepository.save(new Student());
	}

	@Test
	public void createWhenEmailIsNullSholdThrowConstraintViolationException() {
		thrown.expect(ConstraintViolationException.class);
		Student student = new Student();
		student.setName("Caesar Zeppeli");
		this.studentRepository.save(student);
	}
	
	@Test
	public void createWhenEmailIsNotValidSholdThrowConstraintViolationException() {
		thrown.expect(ConstraintViolationException.class);
		thrown.expectMessage("Enter a valid email");
		Student student = new Student();
		student.setName("Caesar Zeppeli");
		student.setEmail("caesar.zeppeli");
		this.studentRepository.save(student);
	}
	
}
