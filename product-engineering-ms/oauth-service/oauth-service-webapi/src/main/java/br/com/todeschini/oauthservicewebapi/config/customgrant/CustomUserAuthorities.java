package br.com.todeschini.oauthservicewebapi.config.customgrant;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class CustomUserAuthorities {

    private final Integer userId;
    private final String username;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserAuthorities(Integer userId, String username, Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.username = username;
        this.authorities = authorities;
    }
}
