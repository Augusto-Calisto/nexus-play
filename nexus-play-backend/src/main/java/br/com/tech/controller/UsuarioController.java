package br.com.tech.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tech.dto.response.UsuarioInfoResponse;
import br.com.tech.entity.Regra;
import br.com.tech.entity.Usuario;
import br.com.tech.repository.UsuarioRepository;

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioController {
	private UsuarioRepository usuarioRepository;

	public UsuarioController(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	@GetMapping(value = "/info")
	public ResponseEntity<UsuarioInfoResponse> buscarDadosUsuario(Authentication authentication) {
        String email = authentication.getPrincipal().toString(); // authentication já vem populado pelo JwtCookieAuthFilter
                        
		Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

		List<String> regrasString = usuario.getRegras().stream()
											.map(Regra::getNome)
											.collect(Collectors.toList());
		
		return ResponseEntity.ok(new UsuarioInfoResponse(
        	usuario.getId(),
        	usuario.getNome(),
        	usuario.getUsername(),
        	usuario.getEmail(),
        	regrasString
        ));
	}
}
