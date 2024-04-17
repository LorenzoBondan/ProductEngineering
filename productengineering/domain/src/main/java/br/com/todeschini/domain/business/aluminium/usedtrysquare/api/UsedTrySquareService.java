package br.com.todeschini.domain.business.aluminium.usedtrysquare.api;

import org.springframework.stereotype.Component;

@Component
public interface UsedTrySquareService extends FindUsedTrySquare, InsertUsedTrySquare, UpdateUsedTrySquare, DeleteUsedTrySquare, InactivateUsedTrySquare,
        FindAllActiveUsedTrySquareAndCurrentOne {
}
