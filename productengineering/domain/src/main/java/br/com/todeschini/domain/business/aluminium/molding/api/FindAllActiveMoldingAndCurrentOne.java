package br.com.todeschini.domain.business.aluminium.molding.api;

import br.com.todeschini.domain.business.aluminium.molding.DMolding;

import java.util.List;

public interface FindAllActiveMoldingAndCurrentOne {

    List<DMolding> findAllActiveAndCurrentOne (Long id);
}
