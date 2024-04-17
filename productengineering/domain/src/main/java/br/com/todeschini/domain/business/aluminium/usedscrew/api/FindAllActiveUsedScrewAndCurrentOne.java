package br.com.todeschini.domain.business.aluminium.usedscrew.api;

import br.com.todeschini.domain.business.aluminium.usedscrew.DUsedScrew;

import java.util.List;

public interface FindAllActiveUsedScrewAndCurrentOne {

    List<DUsedScrew> findAllActiveAndCurrentOne (Long id);
}
