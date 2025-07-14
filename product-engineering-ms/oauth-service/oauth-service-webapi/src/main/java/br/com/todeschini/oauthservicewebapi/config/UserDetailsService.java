package br.com.todeschini.oauthservicewebapi.config;

import br.com.todeschini.oauthservicedomain.user.DUser;
import br.com.todeschini.oauthservicedomain.user.api.UserService;
import br.com.todeschini.oauthservicepersistence.user.UserDomainToEntityAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserService userService;
    private final UserDomainToEntityAdapter adapter;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        DUser result = userService.findByEmail(username);
        return adapter.toEntity(result);
    }
}
