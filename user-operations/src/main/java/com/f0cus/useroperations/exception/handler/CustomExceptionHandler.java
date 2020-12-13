package com.f0cus.useroperations.exception.handler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.f0cus.useroperations.exception.GenericException;

@RestController
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<GenericException> handleAllException(Exception exception, WebRequest request) {
		GenericException gexp = new GenericException(LocalDateTime.now(), exception.getMessage(),request.getDescription(false));
		ResponseEntity<GenericException> response = new ResponseEntity<>(gexp,HttpStatus.INTERNAL_SERVER_ERROR);
		return response;
	}
}
