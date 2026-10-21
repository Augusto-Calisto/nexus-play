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
import br.com.tech.exception.RegraNaoExisteException;
import br.com.tech.exception.UsuarioExistenteException;
import br.com.tech.repository.RegraRepository;
import br.com.tech.repository.UsuarioRepository;

@Service
public class CustomOidcUserService extends OidcUserService {
	private final UsuarioRepository usuarioRepository;
	private final RegraRepository regraRepository;

	public CustomOidcUserService(UsuarioRepository usuarioRepository, RegraRepository regraRepository) {
		this.usuarioRepository = usuarioRepository;
		this.regraRepository = regraRepository;
	}
	
    @Override
    public OidcUser loadUser(OidcUserRequest req) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(req);
                
        String email = oidcUser.getEmail();
        
        Optional<Usuario> optionalUsuario = usuarioRepository.findByEmail(email);
        
    	String nomeProvedor = req.getClientRegistration().getRegistrationId().toUpperCase();
        
        if(optionalUsuario.isPresent()) {
			Usuario usuario = optionalUsuario.get();
			
			if("LOCAL".equals(usuario.getNomeProvedor())) {
				String mensagem = String.format("Email da conta " + nomeProvedor + " ('%s'), já foi cadastrado através de login e senha", usuario.getEmail());
				
				throw new UsuarioExistenteException(mensagem);
			}
			
		} else {
        	String nomeUsuario = oidcUser.getFullName();
        	        	        	
        	String usernameUsuarioProvedor = oidcUser.getPreferredUsername();
        	
        	String idUsuarioProvedor = oidcUser.getSubject();
        	
        	String username = usernameUsuarioProvedor != null ? usernameUsuarioProvedor : nomeUsuario.toLowerCase();
        	
        	Optional<Regra> optionalRegra = regraRepository.findByNome("ROLE_USUARIO_OAUTH");
        	
    		Regra regra = optionalRegra.orElseThrow(() -> new RegraNaoExisteException("Regra 'ROLE_USUARIO_OAUTH' não existe"));
        	
        	Usuario u = new Usuario(nomeProvedor, idUsuarioProvedor, nomeUsuario, username, email, List.of(regra));
        	
        	usuarioRepository.save(u);
        }

        return oidcUser;
    }
}