package br.com.tech.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tech.dto.request.LoginRequest;
import br.com.tech.service.UsuarioService;

@RestController
@RequestMapping(value = "/auth")
public class AutenticacaoController {
	private UsuarioService usuarioService;
	private AuthenticationManager authenticationManager;

	public AutenticacaoController(UsuarioService usuarioService, AuthenticationManager authenticationManager) {
		this.usuarioService = usuarioService;
		this.authenticationManager = authenticationManager;
	}
	
	@PostMapping(value = "/login")
	public ResponseEntity<HttpStatus> autenticar(@RequestBody LoginRequest req, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(req.email(), req.senha());
		
	    Authentication auth = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
	    
	    usuarioService.onAuthenticationSuccess(request, response, auth);
	    
	    return ResponseEntity.status(HttpStatus.OK).build();
	}
}