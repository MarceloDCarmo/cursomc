package com.mdcarmo.sprgbappbackend.resources.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.mdcarmo.sprgbappbackend.services.exception.AuthorizationException;
import com.mdcarmo.sprgbappbackend.services.exception.DataIntegrityException;
import com.mdcarmo.sprgbappbackend.services.exception.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExcpetionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException ex, HttpServletRequest request){
		StandardError error = new StandardError(HttpStatus.NOT_FOUND.value(), ex.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException ex, HttpServletRequest request){
		StandardError error = new StandardError(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validationException(MethodArgumentNotValidException ex, HttpServletRequest request){
		ValidationError error = new ValidationError(HttpStatus.BAD_REQUEST.value(), "Erro de validação", System.currentTimeMillis());
		for (FieldError e : ex.getBindingResult().getFieldErrors()) {
			error.addError(e.getField(), e.getDefaultMessage());
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<StandardError> authorization(AuthorizationException ex, HttpServletRequest request){
		StandardError error = new StandardError(HttpStatus.FORBIDDEN.value(), ex.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
	}
}
