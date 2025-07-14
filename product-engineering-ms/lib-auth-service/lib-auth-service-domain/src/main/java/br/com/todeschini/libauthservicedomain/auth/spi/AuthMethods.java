package br.com.todeschini.libauthservicedomain.auth.spi;

import br.com.todeschini.libauthservicedomain.user.DUser;

public interface AuthMethods {

    DUser authenticated();
    void validateSelfOrAdmin(Integer id);
    Boolean isAdmin();
}
