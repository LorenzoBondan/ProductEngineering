package br.com.todeschini.domain.business.aluminium.molding.spi;

import br.com.todeschini.domain.SimpleCrud;
import br.com.todeschini.domain.business.aluminium.molding.DMolding;

import java.util.Collection;

public interface CrudMolding extends SimpleCrud<DMolding, Long> {

    Collection<? extends DMolding> findByDescription (String description);
}
