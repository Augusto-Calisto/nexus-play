package br.com.tech.handler;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuth2LoginFailureHandler implements AuthenticationFailureHandler {
	
	@Value("${app.frontend.url}")
    private String frontEndUrl;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		response.setStatus(HttpStatus.CONFLICT.value());
		
		String codigo = "oauth_falhou";
		
        if (exception instanceof OAuth2AuthenticationException oauthEx) {
            codigo = oauthEx.getError().getErrorCode();
        }

        String url = UriComponentsBuilder.fromUriString(frontEndUrl)
						                .queryParam("oauth-error", codigo)
						                .build().toUriString();

        response.sendRedirect(url);
	}
}