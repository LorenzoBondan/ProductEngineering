package br.com.todeschini.domain.business.auth.authservice.spi;

import br.com.todeschini.domain.business.auth.user.DUser;

public interface AuthMethods {

    DUser authenticated();
    void validateSelfOrAdmin(Long userId);
}
