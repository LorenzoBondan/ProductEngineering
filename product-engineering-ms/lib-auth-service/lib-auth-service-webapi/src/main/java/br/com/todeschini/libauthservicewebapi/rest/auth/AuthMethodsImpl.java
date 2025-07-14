package br.com.todeschini.libauthservicewebapi.rest.auth;

import br.com.todeschini.libauthservicedomain.auth.spi.AuthMethods;
import br.com.todeschini.libauthservicedomain.role.DRole;
import br.com.todeschini.libauthservicedomain.user.DUser;
import br.com.todeschini.libauthservicewebapi.utils.CustomUserUtil;
import br.com.todeschini.libexceptionhandler.exceptions.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AuthMethodsImpl implements AuthMethods {

    private final CustomUserUtil customUserUtil;

    @Override
    public DUser authenticated() {
        try {
            return DUser.builder()
                    .id(customUserUtil.getLoggedUserId())
                    .email(customUserUtil.getLoggedUsername())
                    .roles(customUserUtil.getRoles().stream().map(
                            authority -> new DRole(null, authority))
                            .collect(Collectors.toSet()))
                    .build();
        }
        catch (Exception e) {
            throw new UsernameNotFoundException("Invalid user");
        }
    }

    @Override
    public void validateSelfOrAdmin(Integer id) {
        DUser me = authenticated();
        if (me.hasRole("ROLE_ADMIN")) {
            return;
        }
        if(!me.getId().equals(id)) {
            throw new ForbiddenException("Access denied. Should be self or admin");
        }
    }

    @Override
    public Boolean isAdmin() {
        DUser user = authenticated();
        return user.hasRole("ROLE_ADMIN");
    }
}
