package br.com.todeschini.domain.business.mdp.usededgebanding.spi;

import br.com.todeschini.domain.SimpleCrud;
import br.com.todeschini.domain.business.mdp.usededgebanding.DUsedEdgeBanding;

import java.util.Collection;

public interface CrudUsedEdgeBanding extends SimpleCrud<DUsedEdgeBanding, Long> {

    Collection<? extends DUsedEdgeBanding> findByEdgeBandingAndMDPSon (Long EdgeBandingId, Long mdpSonId);
}
