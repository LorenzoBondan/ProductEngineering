package br.com.todeschini.domain.business.auth.role.api;

import br.com.todeschini.domain.business.auth.role.DRole;

public interface UpdateRole {

    DRole update (Long id, DRole Role);
}
