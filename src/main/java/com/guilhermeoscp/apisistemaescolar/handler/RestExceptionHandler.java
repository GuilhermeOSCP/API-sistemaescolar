package com.guilhermeoscp.apisistemaescolar.handler;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.guilhermeoscp.apisistemaescolar.error.ErrorDetails;
import com.guilhermeoscp.apisistemaescolar.error.ResourceNotFoundDetails;
import com.guilhermeoscp.apisistemaescolar.error.ResourceNotFoundException;
import com.guilhermeoscp.apisistemaescolar.error.ValidationErrorDetails;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
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
	
	@Override
	public ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException manvException, HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		List<FieldError> fieldErrors = manvException.getBindingResult().getFieldErrors();
		String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(","));
		String fieldMessages = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(","));
		ValidationErrorDetails rfnDetails = ValidationErrorDetails.Builder
				.newBuilder()
				.timestamp(new Date().getTime())
				.status(HttpStatus.BAD_REQUEST.value())
				.title("Field Validation Error")
				.detail("The arguments entered are invalid")
				.developerMessage(manvException.getClass().getName())
				.field(fields)
				.fieldMessage(fieldMessages)
				.builder();
		return new ResponseEntity<>(rfnDetails, HttpStatus.BAD_REQUEST);		
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ErrorDetails errorDetails = ErrorDetails.Builder
				.newBuilder()
				.Timestamp(new Date().getTime())
				.Status(status.value())
				.Title("Internal Exception")
				.Detail(ex.getMessage())
				.DeveloperMessage(ex.getClass().getName())
				.builder();
		return new ResponseEntity<>(errorDetails, headers, status);
	}
}
