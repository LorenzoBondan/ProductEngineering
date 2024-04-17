package br.com.todeschini.domain.business.aluminium.trysquare.api;

import org.springframework.stereotype.Component;

@Component
public interface TrySquareService extends FindTrySquare, InsertTrySquare, UpdateTrySquare, DeleteTrySquare, InactivateTrySquare,
        FindAllActiveTrySquareAndCurrentOne {
}
