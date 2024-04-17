package br.com.todeschini.domain.business.packaging.ghost.spi;

import br.com.todeschini.domain.SimpleCrud;
import br.com.todeschini.domain.business.packaging.ghost.DGhost;

import java.util.Collection;

public interface CrudGhost extends SimpleCrud<DGhost, String> {

    Collection<? extends DGhost> findByDescription (String description);
}
