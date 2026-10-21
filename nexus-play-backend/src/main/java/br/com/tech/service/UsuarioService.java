package br.com.tech.service;

import java.io.IOException;
import java.util.List;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import br.com.tech.dto.request.CadastroUsuario;
import br.com.tech.entity.Regra;
import br.com.tech.entity.Usuario;
import br.com.tech.exception.RegraNaoExisteException;
import br.com.tech.exception.UsuarioExistenteException;
import br.com.tech.repository.RegraRepository;
import br.com.tech.repository.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService, AuthenticationSuccessHandler {
	private final UsuarioRepository usuarioRepository;
	private final RegraRepository regraRepository;
	private final JwtService jwtService;
	private final AuthCookieFactory cookieFactory;
	private final PasswordEncoder passwordEncoder;

	public UsuarioService(UsuarioRepository usuarioRepository, RegraRepository regraRepository, JwtService jwtService, AuthCookieFactory cookieFactory, PasswordEncoder passwordEncoder) {
		this.usuarioRepository = usuarioRepository;
		this.regraRepository = regraRepository;
		this.jwtService = jwtService;
		this.cookieFactory = cookieFactory;
		this.passwordEncoder = passwordEncoder;
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
	
	public Usuario cadastrarNovoUsuario(CadastroUsuario cadastroUsuario) {
		String username = cadastroUsuario.username();
		
		String email = cadastroUsuario.email().toLowerCase();
		
		Optional<Usuario> optionalUsuario = usuarioRepository.findByUsernameOrEmail(username, email);
		
		if(optionalUsuario.isPresent()) {
			String mensagemErro = "";
			
			Usuario usuario = optionalUsuario.get();
			
			if (usuario.getUsername().equalsIgnoreCase(username)) {
				mensagemErro = "Username '" + username + "' já existe. Utilize outro username";
				
			} else {
				mensagemErro = "Email '" + email + "' já existe. Utilize outro email";
			}
			
			throw new UsuarioExistenteException(mensagemErro);
		}
		
		String nome = cadastroUsuario.nome().toUpperCase();
		
		String senhaCriptografada = passwordEncoder.encode(cadastroUsuario.senha());
		
		Optional<Regra> optionalRegra = regraRepository.findByNome("ROLE_USUARIO_LOCAL");
		
		Regra regra = optionalRegra.orElseThrow(() -> new RegraNaoExisteException("Regra 'ROLE_USUARIO_LOCAL' não existe"));
						
		Usuario usuario = new Usuario(nome, username, email, senhaCriptografada, List.of(regra));
					
		return usuarioRepository.saveAndFlush(usuario);
	}
}