package com.guilhermeoscp.apisistemaescolar.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.guilhermeoscp.apisistemaescolar.model.Student;

public interface StudentRepository extends CrudRepository <Student, Long>{
	List<Student> findByNameIgnoreCaseContaining(String name);
}
