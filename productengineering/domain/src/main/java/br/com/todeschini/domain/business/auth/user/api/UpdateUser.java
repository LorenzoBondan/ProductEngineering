package br.com.todeschini.domain.business.auth.user.api;

import br.com.todeschini.domain.business.auth.user.DUser;

public interface UpdateUser {

    DUser update (Long id, DUser user);
}
