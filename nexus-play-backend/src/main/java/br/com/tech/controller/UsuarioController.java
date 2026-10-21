package br.com.tech.controller;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tech.dto.request.CadastroUsuario;
import br.com.tech.dto.response.UsuarioInfoResponse;
import br.com.tech.entity.Regra;
import br.com.tech.entity.Usuario;
import br.com.tech.service.UsuarioService;

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioController {
	private final UsuarioService usuarioService;

	public UsuarioController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	@GetMapping(value = "/info")
	public ResponseEntity<UsuarioInfoResponse> buscarDadosUsuario(Authentication authentication, HttpServletRequest request) {
        String email = authentication.getPrincipal().toString(); // authentication já vem populado pelo JwtCookieAuthFilter
                        
		Usuario usuario = (Usuario) usuarioService.loadUserByUsername(email);

		List<String> regrasString = usuario.getRegras().stream()
											.map(Regra::getNome)
											.collect(Collectors.toList());
			
		UsuarioInfoResponse body = new UsuarioInfoResponse(
        	usuario.getId(),
        	usuario.getNome(),
        	usuario.getUsername(),
        	usuario.getEmail(),
        	regrasString
        );
				
		return ResponseEntity.ok(body);
	}
	
	@PostMapping(value = "/cadastrar")
	public ResponseEntity<HttpStatus> cadastrarNovoUsuario(@Valid @RequestBody CadastroUsuario cadastroUsuario) {
		usuarioService.cadastrarNovoUsuario(cadastroUsuario);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}