package br.com.todeschini.domain.business.aluminium.trysquare.api;

import br.com.todeschini.domain.business.aluminium.trysquare.DTrySquare;

import java.util.List;

public interface FindAllActiveTrySquareAndCurrentOne {

    List<DTrySquare> findAllActiveAndCurrentOne (Long id);
}
