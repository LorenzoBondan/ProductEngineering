package br.com.todeschini.libauthservicedomain.auth;

import br.com.todeschini.libauthservicedomain.auth.api.AuthService;
import br.com.todeschini.libauthservicedomain.auth.spi.AuthMethods;
import br.com.todeschini.libauthservicedomain.user.DUser;
import br.com.todeschini.libvalidationhandler.metadata.DomainService;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthMethods authMethods;

    @Override
    public DUser authenticated() {
        return authMethods.authenticated();
    }

    @Override
    public Boolean isAdmin() {
        return authMethods.isAdmin();
    }

    @Override
    public void validateSelfOrAdmin(Integer id) {
        authMethods.validateSelfOrAdmin(id);
    }
}
