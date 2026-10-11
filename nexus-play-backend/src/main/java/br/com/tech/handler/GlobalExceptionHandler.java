package br.com.tech.handler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.tech.dto.response.ExceptionResponse;

@RestController
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value = AuthenticationException.class)
	public ResponseEntity<ExceptionResponse> handlerAuthenticationException(AuthenticationException authenticationException) {
		HttpStatus UNAUTHORIZED = HttpStatus.UNAUTHORIZED;
		
		ExceptionResponse exceptionResponse = new ExceptionResponse(
			authenticationException.getMessage(), 
			UNAUTHORIZED.value(),
			LocalDateTime.now().toString()
		);
		
		return ResponseEntity.status(UNAUTHORIZED).body(exceptionResponse);
	}
}