package br.com.tech.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import br.com.tech.entity.Regra;
import br.com.tech.entity.Usuario;
import br.com.tech.exception.UsuarioExistenteException;
import br.com.tech.repository.UsuarioRepository;

@Service
public class CustomOidcUserService extends OidcUserService {
	private UsuarioRepository usuarioRepository;

	public CustomOidcUserService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}
	
    @Override
    public OidcUser loadUser(OidcUserRequest req) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(req);
                
        String email = oidcUser.getEmail();
        
        Optional<Usuario> optional = usuarioRepository.findByEmail(email);
        
        if(optional.isPresent()) {
			Usuario usuario = optional.get();
			
			if("LOCAL".equals(usuario.getNomeProvedor())) {
				String mensagem = String.format("Usuario com email '%s' já foi cadastrado", usuario.getEmail());
				throw new UsuarioExistenteException(mensagem);
			}
			
		} else {
        	String nomeUsuario = oidcUser.getFullName();
        	
        	String nomeProvedor = req.getClientRegistration().getRegistrationId().toUpperCase();
        	        	
        	String usernameUsuarioProvedor = oidcUser.getPreferredUsername();
        	
        	String idUsuarioProvedor = oidcUser.getSubject();
        	
        	String username = usernameUsuarioProvedor != null ? usernameUsuarioProvedor : nomeUsuario.toLowerCase();
        	
        	Usuario u = new Usuario(nomeProvedor, idUsuarioProvedor, nomeUsuario, username, email, List.of(new Regra("ROLE_USUARIO_OAUTH")));
        	
        	usuarioRepository.save(u);
        }

        return oidcUser;
    }
}