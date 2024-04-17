package br.com.todeschini.domain.business.publico.son.spi;

import br.com.todeschini.domain.SimpleCrud;
import br.com.todeschini.domain.business.publico.son.DSon;

import java.util.Collection;

public interface CrudSon extends SimpleCrud<DSon, Long> {

    Collection<? extends DSon> findByDescription (String description);
}
