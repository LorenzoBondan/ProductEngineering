package br.com.todeschini.userservicedomain.user.api;

import br.com.todeschini.libvalidationhandler.contracts.BaseService;
import br.com.todeschini.userservicedomain.user.DUser;

public interface UserService extends BaseService<DUser> {

    DUser findMe();
    DUser find (String email);
    void updatePassword (String newPassword, String oldPassword);
}
