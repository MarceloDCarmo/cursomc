package com.mdcarmo.sprgbappbackend.resources.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {

	private List<FieldErrorMessage> errors = new ArrayList<>();
	
	public ValidationError(Integer status, String msg, Long timeStamp) {
		super(status, msg, timeStamp);
	}

	private static final long serialVersionUID = 1L;

	public List<FieldErrorMessage> getErrors() {
		return errors;
	}
	
	public void addError(String fieldName, String message) {
		errors.add(new FieldErrorMessage(fieldName, message));
	}
}
