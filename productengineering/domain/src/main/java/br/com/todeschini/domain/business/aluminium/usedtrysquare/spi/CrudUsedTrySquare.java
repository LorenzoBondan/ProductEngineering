package br.com.todeschini.domain.business.aluminium.usedtrysquare.spi;

import br.com.todeschini.domain.SimpleCrud;
import br.com.todeschini.domain.business.aluminium.usedtrysquare.DUsedTrySquare;

import java.util.Collection;

public interface CrudUsedTrySquare extends SimpleCrud<DUsedTrySquare, Long> {

    Collection<? extends DUsedTrySquare> findByTrySquareAndAluminiumSon (Long TrySquareId, Long aluminiumSonId);
}
