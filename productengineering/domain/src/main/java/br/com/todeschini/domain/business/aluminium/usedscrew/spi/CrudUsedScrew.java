package br.com.todeschini.domain.business.aluminium.usedscrew.spi;

import br.com.todeschini.domain.SimpleCrud;
import br.com.todeschini.domain.business.aluminium.usedscrew.DUsedScrew;

import java.util.Collection;

public interface CrudUsedScrew extends SimpleCrud<DUsedScrew, Long> {

    Collection<? extends DUsedScrew> findByScrewAndAluminiumSon (Long ScrewId, Long aluminiumSonId);
}
