package br.com.todeschini.domain.business.aluminium.usedmolding.spi;

import br.com.todeschini.domain.SimpleCrud;
import br.com.todeschini.domain.business.aluminium.usedmolding.DUsedMolding;

import java.util.Collection;

public interface CrudUsedMolding extends SimpleCrud<DUsedMolding, Long> {

    Collection<? extends DUsedMolding> findByMoldingAndAluminiumSon (Long MoldingId, Long aluminiumSonId);
}
