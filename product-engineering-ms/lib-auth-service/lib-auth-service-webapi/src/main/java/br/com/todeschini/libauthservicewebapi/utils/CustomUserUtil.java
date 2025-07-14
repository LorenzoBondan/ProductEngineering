package br.com.todeschini.libauthservicewebapi.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomUserUtil {

    private Jwt getJwt() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (Jwt) authentication.getPrincipal();
    }

    public String getLoggedUsername() {
        return getJwt().getClaim("username");
    }

    public List<String> getRoles() {
        return getJwt().getClaimAsStringList("authorities");
    }

    public Integer getLoggedUserId() {
        return Integer.parseInt(getJwt().getClaim("user_id").toString());
    }
}
