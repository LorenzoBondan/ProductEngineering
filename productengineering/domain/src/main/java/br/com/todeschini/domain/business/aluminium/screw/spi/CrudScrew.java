package br.com.todeschini.domain.business.aluminium.screw.spi;

import br.com.todeschini.domain.SimpleCrud;
import br.com.todeschini.domain.business.aluminium.screw.DScrew;

import java.util.Collection;

public interface CrudScrew extends SimpleCrud<DScrew, Long> {

    Collection<? extends DScrew> findByDescription (String description);
}
