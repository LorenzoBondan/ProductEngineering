package br.com.todeschini.domain.business.aluminium.trysquare.spi;

import br.com.todeschini.domain.SimpleCrud;
import br.com.todeschini.domain.business.aluminium.trysquare.DTrySquare;

import java.util.Collection;

public interface CrudTrySquare extends SimpleCrud<DTrySquare, Long> {

    Collection<? extends DTrySquare> findByDescription (String description);
}
