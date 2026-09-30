package br.com.tech.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import br.com.tech.filter.JwtCookieAuthFilter;
import br.com.tech.handler.OAuth2LoginSuccessHandler;
import br.com.tech.service.CustomOidcUserService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SegurityConfig {
	private CustomOidcUserService customOidcUserService;
	private JwtCookieAuthFilter jwtAuthFilter;

	@Value(value = "${spring.security.oauth2.client.provider.google.issuer-uri}")
	private String uriProvedorIdentidade;
	
	@Value(value = "${app.frontend.url}")
	private String urlFrontEnd;

	public SegurityConfig(CustomOidcUserService customOidcUserService, JwtCookieAuthFilter jwtAuthFilter) {
		this.customOidcUserService = customOidcUserService;
		this.jwtAuthFilter = jwtAuthFilter;
	}
	
	@Bean
    JwtDecoder jwtDecoder() {
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withIssuerLocation(uriProvedorIdentidade).build();
        return jwtDecoder;
    }

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
	    return configuration.getAuthenticationManager();
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http, OAuth2LoginSuccessHandler successHandler) throws Exception {
	    http
		    .cors((cors) -> {
				cors.configurationSource((req) -> {
					CorsConfiguration corsConfiguration = new CorsConfiguration();
					corsConfiguration.addAllowedOrigin(urlFrontEnd);
					corsConfiguration.setAllowCredentials(true);
					return corsConfiguration;
				});
			})
		    
	        .csrf(AbstractHttpConfigurer::disable)
	        
	        .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	        
	        .authorizeHttpRequests(auth -> auth
	        	.requestMatchers("/oauth2/**", "/auth/login").permitAll()
	            .anyRequest().authenticated()
	        )
	        
	        .oauth2Login(oauth2 -> oauth2
	            .userInfoEndpoint(u -> u.oidcUserService(customOidcUserService))
	            .successHandler(successHandler)
	            .failureUrl(urlFrontEnd + "?error=true")
	        )
	        
	        .logout(logout -> logout
	        	.logoutUrl("/logout")
	            .deleteCookies("auth_token")
	            .clearAuthentication(true)
	            .logoutSuccessHandler((request, response, authentication) -> {
	            	response.sendRedirect(urlFrontEnd + "/");
	            })
	        )
	        
	        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
	        
	    return http.build();
	}
}