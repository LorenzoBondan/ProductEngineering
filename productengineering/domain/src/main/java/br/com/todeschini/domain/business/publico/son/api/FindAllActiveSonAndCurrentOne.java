package br.com.todeschini.domain.business.publico.son.api;

import br.com.todeschini.domain.business.publico.son.DSon;

import java.util.List;

public interface FindAllActiveSonAndCurrentOne {

    List<DSon> findAllActiveAndCurrentOne (Long id);
}
