package br.com.todeschini.domain.business.auth.role.spi;

import br.com.todeschini.domain.SimpleCrud;
import br.com.todeschini.domain.business.auth.role.DRole;

import java.util.Collection;

public interface CrudRole extends SimpleCrud<DRole, Long> {

    Collection<? extends DRole> findByAuthority (String authority);
}
