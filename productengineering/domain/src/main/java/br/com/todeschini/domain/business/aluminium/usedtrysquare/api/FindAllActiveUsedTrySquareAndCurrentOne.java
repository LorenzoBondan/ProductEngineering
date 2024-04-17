package br.com.todeschini.domain.business.aluminium.usedtrysquare.api;

import br.com.todeschini.domain.business.aluminium.usedtrysquare.DUsedTrySquare;

import java.util.List;

public interface FindAllActiveUsedTrySquareAndCurrentOne {

    List<DUsedTrySquare> findAllActiveAndCurrentOne (Long id);
}
