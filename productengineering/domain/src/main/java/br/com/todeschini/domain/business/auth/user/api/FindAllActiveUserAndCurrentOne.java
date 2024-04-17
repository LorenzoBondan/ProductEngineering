package br.com.todeschini.domain.business.auth.user.api;

import br.com.todeschini.domain.business.auth.user.DUser;

import java.util.List;

public interface FindAllActiveUserAndCurrentOne {

    List<DUser> findAllActiveAndCurrentOne (Long id);
}
