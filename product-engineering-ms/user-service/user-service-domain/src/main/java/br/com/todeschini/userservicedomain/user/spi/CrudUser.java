package br.com.todeschini.userservicedomain.user.spi;

import br.com.todeschini.libvalidationhandler.contracts.SimpleCrud;
import br.com.todeschini.userservicedomain.user.DUser;

import java.util.List;

public interface CrudUser extends SimpleCrud<DUser, Integer> {

    List<DUser> findByEmail (String email);
    void updatePassword (String newPassword, String oldPassword, DUser user);
}
