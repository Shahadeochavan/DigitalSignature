package com.nextech.erp.exception;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@ControllerAdvice

public class NextechERPExceptionAdvice{

	
	public NextechERPExceptionAdvice() {
		super();
	}

	@ExceptionHandler(InvalidUserException.class)
    public ResponseEntity<ErrorResponse> exceptionHandlerInvalidUser(InvalidUserException ex) {
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode(HttpStatus.UNAUTHORIZED.value());
        error.setMessage(ex.getMessage());
        return new ResponseEntity<ErrorResponse>(error, HttpStatus.OK);
    }
	
	@ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exceptionHandlerException(Exception ex) {
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setMessage(ex.getMessage());
        return new ResponseEntity<ErrorResponse>(error, HttpStatus.OK);
    }
	
	@ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(ConstraintViolationException ex) {
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode(HttpStatus.EXPECTATION_FAILED.value());
        error.setMessage(ex.getMessage());
        return new ResponseEntity<ErrorResponse>(error, HttpStatus.OK);
    }
}