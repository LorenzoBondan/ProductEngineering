package br.com.todeschini.domain.business.mdp.usededgebanding.api;

import br.com.todeschini.domain.business.mdp.usededgebanding.DUsedEdgeBanding;

import java.util.List;

public interface FindAllActiveUsedEdgeBandingAndCurrentOne {

    List<DUsedEdgeBanding> findAllActiveAndCurrentOne (Long id);
}
