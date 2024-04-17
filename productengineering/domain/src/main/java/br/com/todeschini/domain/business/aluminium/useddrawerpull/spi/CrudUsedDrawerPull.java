package br.com.todeschini.domain.business.aluminium.useddrawerpull.spi;

import br.com.todeschini.domain.SimpleCrud;
import br.com.todeschini.domain.business.aluminium.useddrawerpull.DUsedDrawerPull;

import java.util.Collection;

public interface CrudUsedDrawerPull extends SimpleCrud<DUsedDrawerPull, Long> {

    Collection<? extends DUsedDrawerPull> findByDrawerPullAndAluminiumSon (Long drawerPullId, Long aluminiumSonId);
}
