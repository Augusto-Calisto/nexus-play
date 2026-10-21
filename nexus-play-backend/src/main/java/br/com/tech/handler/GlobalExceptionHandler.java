package br.com.tech.handler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
			LocalDateTime.now()
		);
		
		return ResponseEntity.status(UNAUTHORIZED).body(exceptionResponse);
	}
	
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<ExceptionResponse> handlerMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {		
		HttpStatus BAD_REQUEST = HttpStatus.BAD_REQUEST;
		
		List<String> mensagensErro = methodArgumentNotValidException.getBindingResult()
	    					.getAllErrors().stream()
							.map((campoComErro) -> campoComErro.getDefaultMessage())
							.collect(Collectors.toList());
		
		ExceptionResponse exceptionResponse = new ExceptionResponse(
			mensagensErro, 
			BAD_REQUEST.value(),
			LocalDateTime.now()
		);
	    
	    return ResponseEntity.status(BAD_REQUEST).body(exceptionResponse);
	}
	
	@ExceptionHandler(value = DisabledException.class)
	public ResponseEntity<ExceptionResponse> handlerDisabledException(DisabledException disabledException) {		
		HttpStatus BAD_REQUEST = HttpStatus.BAD_REQUEST;
		
		ExceptionResponse exceptionResponse = new ExceptionResponse(
			disabledException.getMessage() + ". Verifique seu email e valide sua conta.", 
			BAD_REQUEST.value(),
			LocalDateTime.now()
		);
			    
	    return ResponseEntity.status(BAD_REQUEST).body(exceptionResponse);
	}
}