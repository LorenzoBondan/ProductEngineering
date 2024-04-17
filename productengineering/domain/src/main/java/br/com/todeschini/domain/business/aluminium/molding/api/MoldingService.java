package br.com.todeschini.domain.business.aluminium.molding.api;

import org.springframework.stereotype.Component;

@Component
public interface MoldingService extends FindMolding, InsertMolding, UpdateMolding, DeleteMolding, InactivateMolding,
        FindAllActiveMoldingAndCurrentOne {
}
