package br.com.todeschini.domain.business.mdp.edgebanding.spi;

import br.com.todeschini.domain.SimpleCrud;
import br.com.todeschini.domain.business.mdp.edgebanding.DEdgeBanding;

import java.util.Collection;

public interface CrudEdgeBanding extends SimpleCrud<DEdgeBanding, Long> {

    Collection<? extends DEdgeBanding> findByDescription (String description);
}
