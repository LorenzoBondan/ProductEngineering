package br.com.todeschini.domain.business.mdp.glue.api;

import br.com.todeschini.domain.business.mdp.glue.DGlue;

import java.util.List;

public interface FindAllActiveGlueAndCurrentOne {

    List<DGlue> findAllActiveAndCurrentOne (Long id);
}
