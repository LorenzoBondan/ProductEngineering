package br.com.todeschini.domain.business.aluminium.screw.api;

import br.com.todeschini.domain.business.aluminium.screw.DScrew;

import java.util.List;

public interface FindAllActiveScrewAndCurrentOne {

    List<DScrew> findAllActiveAndCurrentOne (Long id);
}
