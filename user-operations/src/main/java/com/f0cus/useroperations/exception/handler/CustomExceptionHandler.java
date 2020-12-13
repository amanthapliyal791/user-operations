package com.f0cus.useroperations.exception.handler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.f0cus.useroperations.exception.GenericException;
import com.f0cus.useroperations.exception.UserNotFoundException;

@RestController
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleAllException(Exception exception, WebRequest request) {
		GenericException gexp = new GenericException(LocalDateTime.now(), exception.getMessage(),request.getDescription(false));
		ResponseEntity<Object> response = new ResponseEntity<>(gexp,HttpStatus.INTERNAL_SERVER_ERROR);
		return response;
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<Object> handleNotFoundException(Exception exception, WebRequest request) {
		GenericException gexp = new GenericException(LocalDateTime.now(), exception.getMessage(),request.getDescription(false));
		ResponseEntity<Object> response = new ResponseEntity<>(gexp,HttpStatus.NOT_FOUND);
		return response;
	}
	
	@Override
	public ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		GenericException gexp = new GenericException(LocalDateTime.now(), 
									"Invalid Argument In Input",
									ex.getBindingResult().getAllErrors().stream()
																		.map((x) -> x.toString())
																		.collect(Collectors.joining(",")));
		ResponseEntity<Object> response = new ResponseEntity<>(gexp,HttpStatus.BAD_REQUEST);
		return response;
	}
}
