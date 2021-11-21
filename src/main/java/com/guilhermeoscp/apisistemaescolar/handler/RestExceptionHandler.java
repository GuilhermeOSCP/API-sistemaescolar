package com.guilhermeoscp.apisistemaescolar.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.guilhermeoscp.apisistemaescolar.error.ResourceNotFoundDetails;
import com.guilhermeoscp.apisistemaescolar.error.ResourceNotFoundException;

@ControllerAdvice
public class RestExceptionHandler {
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException rfnException) {
		ResourceNotFoundDetails rfnDetails = ResourceNotFoundDetails.Builder
				.newBuilder()
				.timestamp(new Date().getTime())
				.status(HttpStatus.NOT_FOUND.value())
				.title("Resource not Found")
				.detail(rfnException.getMessage())
				.developerMessage(rfnException.getClass().getName())
				.builder();
		return new ResponseEntity<>(rfnDetails, HttpStatus.NOT_FOUND);		
	}
}
