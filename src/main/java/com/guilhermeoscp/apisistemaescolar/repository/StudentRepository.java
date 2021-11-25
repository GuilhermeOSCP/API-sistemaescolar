package com.guilhermeoscp.apisistemaescolar.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.guilhermeoscp.apisistemaescolar.model.Student;

//O extends é para ter paginação (Pacote Adapter), sem demorar muito para carregar a página na Web
public interface StudentRepository extends PagingAndSortingRepository <Student, Long>{
	List<Student> findByNameIgnoreCaseContaining(String name);
}
