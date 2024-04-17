package br.com.todeschini.domain.business.mdp.edgebanding.api;

import br.com.todeschini.domain.business.mdp.edgebanding.DEdgeBanding;

import java.util.List;

public interface FindAllActiveEdgeBandingAndCurrentOne {

    List<DEdgeBanding> findAllActiveAndCurrentOne (Long id);
}
