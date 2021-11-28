package com.guilhermeoscp.apisistemaescolar.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.guilhermeoscp.apisistemaescolar.model.User;

public interface UserRepository extends PagingAndSortingRepository <User, Long>{
	User findByUsername(String username);
}
