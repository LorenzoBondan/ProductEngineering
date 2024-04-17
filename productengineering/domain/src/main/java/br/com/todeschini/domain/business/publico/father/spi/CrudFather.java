package br.com.todeschini.domain.business.publico.father.spi;

import br.com.todeschini.domain.SimpleCrud;
import br.com.todeschini.domain.business.publico.father.DFather;

import java.util.Collection;

public interface CrudFather extends SimpleCrud<DFather, Long> {

    Collection<? extends DFather> findByDescription (String description);
}
