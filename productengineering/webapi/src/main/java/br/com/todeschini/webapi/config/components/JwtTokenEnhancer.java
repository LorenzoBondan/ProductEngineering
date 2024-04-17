package br.com.todeschini.webapi.config.components;

import br.com.todeschini.persistence.auth.user.UserRepository;
import br.com.todeschini.persistence.entities.auth.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenEnhancer implements TokenEnhancer {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		
		User user = userRepository.findByEmail(authentication.getName()).iterator().next();
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("userName", user.getEmail());
		map.put("userId", user.getId());
		
		DefaultOAuth2AccessToken token =  (DefaultOAuth2AccessToken) accessToken;
		token.setAdditionalInformation(map);
		
		return accessToken;
	}

}
