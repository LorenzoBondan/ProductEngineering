package br.com.todeschini.domain.business.packaging.ghost.api;

import br.com.todeschini.domain.business.packaging.ghost.DGhost;

import java.util.List;

public interface FindAllActiveGhostAndCurrentOne {

    List<DGhost> findAllActiveAndCurrentOne (String id);
}
