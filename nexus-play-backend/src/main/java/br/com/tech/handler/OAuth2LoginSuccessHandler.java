package br.com.tech.handler;

import java.io.IOException;
import java.time.Duration;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import br.com.tech.entity.Regra;
import br.com.tech.entity.Usuario;
import br.com.tech.repository.UsuarioRepository;
import br.com.tech.service.JwtService;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtService jwtService;
	private final UsuarioRepository usuarioRepository;

    @Value("${app.frontend.url}")
    private String frontEndUrl;
    
    @Value("${app.jwt.expiracao-ms}")
    private long expiracaoMs;
    
    public OAuth2LoginSuccessHandler(JwtService jwtService, UsuarioRepository usuarioRepository) {
		this.jwtService = jwtService;
		this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    	OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
                
        String email = oauthUser.getAttribute("email");
                
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Usuario com email " + email + " não encontrado"));

        Set<String> regras = usuario.getRegras().stream()
		                		.map(Regra::getAuthority)
		                		.collect(Collectors.toSet());

        // gera o JWT unificado que vocês já têm (mesmo usado no login user/senha)
        String jwt = jwtService.gerarToken(email, regras);

        ResponseCookie cookie = ResponseCookie.from("auth_token", jwt)
                .httpOnly(true)
                .secure(false)                // exige HTTPS (obrigatório em prod)
                .sameSite("Lax")            // "None" se front/back forem domínios diferentes
                .path("/")
                .maxAge(Duration.ofMillis(expiracaoMs))
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        // redireciona sem NENHUM dado sensível na URL
        response.sendRedirect(frontEndUrl + "/oauth-callback");
    }
}