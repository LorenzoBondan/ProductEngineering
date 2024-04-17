package br.com.todeschini.domain.business.mdp.glue.spi;

import br.com.todeschini.domain.SimpleCrud;
import br.com.todeschini.domain.business.mdp.glue.DGlue;

import java.util.Collection;

public interface CrudGlue extends SimpleCrud<DGlue, Long> {

    Collection<? extends DGlue> findByDescription (String description);
}
