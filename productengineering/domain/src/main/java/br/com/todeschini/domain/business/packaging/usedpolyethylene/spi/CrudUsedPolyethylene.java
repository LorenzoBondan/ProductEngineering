package br.com.todeschini.domain.business.packaging.usedpolyethylene.spi;

import br.com.todeschini.domain.SimpleCrud;
import br.com.todeschini.domain.business.packaging.usedpolyethylene.DUsedPolyethylene;

import java.util.Collection;

public interface CrudUsedPolyethylene extends SimpleCrud<DUsedPolyethylene, Long> {

    Collection<? extends DUsedPolyethylene> findByPolyethyleneAndGhost (Long PolyethyleneId, String ghostId);
}
