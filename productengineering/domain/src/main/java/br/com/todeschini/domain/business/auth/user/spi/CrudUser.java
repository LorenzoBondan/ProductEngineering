package br.com.todeschini.domain.business.auth.user.spi;

import br.com.todeschini.domain.SimpleCrud;
import br.com.todeschini.domain.business.auth.user.DUser;

import java.util.Collection;

public interface CrudUser extends SimpleCrud<DUser, Long> {

    Collection<? extends DUser> findByEmail (String email);

    void updatePassword (String newPassword, String oldPassword);
}
