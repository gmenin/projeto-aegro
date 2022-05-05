/**
 * 
 */
package com.aegro.projetoaegro.rest.advice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Gabriel Menin
 *
 */
@RestControllerAdvice
public class ExceptionControllerAdvice {
	
	@Autowired
	private MessageSource messageSource;
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
		
		BindingResult bindingResult = exception.getBindingResult();
		HttpHeaders headers = new HttpHeaders();
		
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		String errors = "";
		
		for(FieldError error: fieldErrors) {
			String message = messageSource.getMessage(error, LocaleContextHolder.getLocale());
			String field = error.getField();
			errors = errors + " " + field + " " + message;
		}
		
		if(bindingResult.hasErrors()) {
			headers.add("Errors", errors);
		}
		
		return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
	}
}
