package br.com.tech.service;

import java.util.Date;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 * Responsável por emitir e validar o JWT que representa a sessão do usuário,
 * independentemente de ele ter se autenticado via login local ou via OAuth2 (Google).
 * Os dois fluxos convergem aqui: qualquer um deles chama gerarToken() com o mesmo formato de claims.
 */
@Service
public class JwtService {
    private final SecretKey chaveSecreta;
    private final long expiracaoMs;

    public JwtService(@Value("${app.jwt.secret}") String secret, @Value("${app.jwt.expiracao-ms}") long expiracaoMs) {
        this.chaveSecreta = Keys.hmacShaKeyFor(secret.getBytes());
        this.expiracaoMs = expiracaoMs;
    }

    public String gerarToken(String identificador, Set<String> roles) {
        Date dataAtual = new Date();
        
        Date dataExpiracao = new Date(dataAtual.getTime() + expiracaoMs);

        return Jwts.builder()
                .subject(identificador)
                .claim("roles", roles)
                .issuedAt(dataAtual)
                .expiration(dataExpiracao)
                .signWith(chaveSecreta)
                .compact();
    }
    
    private Claims extrairClaims(String token) {
        return Jwts.parser()
                .verifyWith(chaveSecreta)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extrairIdentificador(String token) {
        return extrairClaims(token).getSubject();
    }

    @SuppressWarnings("unchecked")
    public Set<String> extrairRoles(String token) {
        return (Set<String>) extrairClaims(token).get("roles", Set.class);
    }

    public boolean tokenValido(String token) {
        try {
            Claims claims = extrairClaims(token);
            
            return claims.getExpiration().after(new Date());
            
        } catch (ExpiredJwtException e) {
            return false; // expirado
            
        } catch (JwtException | IllegalArgumentException e) {
            return false; // malformado, assinatura inválida, etc.
        }
    }
}