package br.com.todeschini.domain.business.mdp.usedglue.api;

import br.com.todeschini.domain.business.mdp.usedglue.DUsedGlue;

import java.util.List;

public interface FindAllActiveUsedGlueAndCurrentOne {

    List<DUsedGlue> findAllActiveAndCurrentOne (Long id);
}
