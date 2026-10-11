package br.com.tech.service;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import br.com.tech.entity.Regra;
import br.com.tech.entity.Usuario;
import br.com.tech.repository.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService, AuthenticationSuccessHandler {
	private final UsuarioRepository usuarioRepository;
	private final JwtService jwtService;
	private final AuthCookieFactory cookieFactory;

	public UsuarioService(UsuarioRepository usuarioRepository, JwtService jwtService, AuthCookieFactory cookieFactory) {
		this.usuarioRepository = usuarioRepository;
		this.jwtService = jwtService;
		this.cookieFactory = cookieFactory;
	}
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		Usuario usuario = (Usuario) authentication.getPrincipal();
 	    
		Set<String> regras = usuario.getRegras().stream()
						        		.map(Regra::getAuthority)
						        		.collect(Collectors.toSet());
		
		String jwt = jwtService.gerarToken(usuario.getEmail(), regras);
				
		ResponseCookie cookie = cookieFactory.criar(jwt);
		
		response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> optional = usuarioRepository.findByEmail(username);
		
		if(optional.isPresent()) {
			return optional.get();
		}
		
		throw new UsernameNotFoundException("Usuario e/ou senha inválidos");
	}

}