package br.com.todeschini.domain.business.aluminium.usedmolding.api;

import br.com.todeschini.domain.business.aluminium.usedmolding.DUsedMolding;

import java.util.List;

public interface FindAllActiveUsedMoldingAndCurrentOne {

    List<DUsedMolding> findAllActiveAndCurrentOne (Long id);
}
