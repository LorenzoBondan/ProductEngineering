package br.com.todeschini.domain.business.packaging.usednonwovenfabric.spi;

import br.com.todeschini.domain.SimpleCrud;
import br.com.todeschini.domain.business.packaging.usednonwovenfabric.DUsedNonwovenFabric;

import java.util.Collection;

public interface CrudUsedNonwovenFabric extends SimpleCrud<DUsedNonwovenFabric, Long> {

    Collection<? extends DUsedNonwovenFabric> findByNonwovenFabricAndGhost (Long nonwovenFabricId, String ghostId);
}
