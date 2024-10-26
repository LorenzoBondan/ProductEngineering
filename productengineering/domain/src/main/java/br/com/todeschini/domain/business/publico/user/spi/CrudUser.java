package br.com.todeschini.domain.business.publico.user.spi;

import br.com.todeschini.domain.SimpleCrud;
import br.com.todeschini.domain.business.publico.user.DUser;

import java.util.Collection;

public interface CrudUser extends SimpleCrud<DUser, Integer> {

    Collection<? extends DUser> findByEmail (String email);
    void updatePassword (String newPassword, String oldPassword);
}
