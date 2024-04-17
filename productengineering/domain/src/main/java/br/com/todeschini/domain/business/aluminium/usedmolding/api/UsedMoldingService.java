package br.com.todeschini.domain.business.aluminium.usedmolding.api;

import org.springframework.stereotype.Component;

@Component
public interface UsedMoldingService extends FindUsedMolding, InsertUsedMolding, UpdateUsedMolding, DeleteUsedMolding, InactivateUsedMolding,
        FindAllActiveUsedMoldingAndCurrentOne {
}
