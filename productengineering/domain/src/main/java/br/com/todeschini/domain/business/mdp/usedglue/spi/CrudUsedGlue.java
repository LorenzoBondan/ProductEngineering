package br.com.todeschini.domain.business.mdp.usedglue.spi;

import br.com.todeschini.domain.SimpleCrud;
import br.com.todeschini.domain.business.mdp.usedglue.DUsedGlue;

import java.util.Collection;

public interface CrudUsedGlue extends SimpleCrud<DUsedGlue, Long> {

    Collection<? extends DUsedGlue> findByGlueAndMDPSon (Long GlueId, Long mdpSonId);
}
