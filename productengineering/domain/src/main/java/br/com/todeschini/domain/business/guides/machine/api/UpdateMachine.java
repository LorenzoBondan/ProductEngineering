package br.com.todeschini.domain.business.guides.machine.api;

import br.com.todeschini.domain.business.guides.machine.DMachine;

public interface UpdateMachine {

    DMachine update (Long id, DMachine Machine);
}
