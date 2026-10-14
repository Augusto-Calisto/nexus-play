package br.com.tech.filter;

import java.io.IOException;
import java.util.Arrays;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.tech.entity.Usuario;
import br.com.tech.service.AuthCookieFactory;
import br.com.tech.service.JwtService;
import br.com.tech.service.UsuarioService;

@Component
public class JwtCookieAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UsuarioService usuarioService;
    private final AuthCookieFactory authCookieFactory;

    public JwtCookieAuthFilter(JwtService jwtService, UsuarioService usuarioService, AuthCookieFactory authCookieFactory) {
        this.jwtService = jwtService;
		this.usuarioService = usuarioService;
		this.authCookieFactory = authCookieFactory;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = extrairTokenCookie(request);
        
        if(token != null) {
            try {
                String email = jwtService.extrairIdentificador(token);

                if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                    if (jwtService.tokenValido(token)) {
                        Usuario usuario = (Usuario) usuarioService.loadUserByUsername(email);

                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(usuario.getEmail(), null, usuario.getAuthorities());
                        
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
                
            } catch (Exception e) {
                SecurityContextHolder.clearContext();
                
                // token inválido/expirado: segue sem autenticar, o endpoint protegido devolve 401 adiante
                
                authCookieFactory.limpar();
            }
        }

        filterChain.doFilter(request, response);
    }

    private String extrairTokenCookie(HttpServletRequest request) {
        if (request.getCookies() == null) return null;        
        
        return Arrays.stream(request.getCookies())
                .filter(c -> AuthCookieFactory.NOME_COOKIE.equals(c.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
    }
}