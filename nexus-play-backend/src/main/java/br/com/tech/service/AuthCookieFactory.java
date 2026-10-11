package br.com.tech.service;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class AuthCookieFactory {
	public static final String NOME_COOKIE = "AUTH-TOKEN-NEXUS";
	
	@Value("${app.jwt.expiracao-ms}")
    private long expiracao;

    public ResponseCookie criar(String jwt) {    	    	
        return ResponseCookie.from(NOME_COOKIE, jwt)
                .httpOnly(true)
                .secure(false) 				  // Mudar depois. exige HTTPS (obrigatório em prod)
                .sameSite("Lax")             // "None" se front/back forem domínios diferentes
                .path("/")
                .maxAge(Duration.ofMillis(expiracao))
                .build();
    }

    public ResponseCookie limpar() {
        return ResponseCookie.from(NOME_COOKIE, "")
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .path("/")
                .maxAge(0)
                .build();
    }
}
