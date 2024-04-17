package br.com.todeschini.domain.business.aluminium.glass.spi;

import br.com.todeschini.domain.SimpleCrud;
import br.com.todeschini.domain.business.aluminium.glass.DGlass;

import java.util.Collection;

public interface CrudGlass extends SimpleCrud<DGlass, Long> {

    Collection<? extends DGlass> findByDescription (String description);
}
