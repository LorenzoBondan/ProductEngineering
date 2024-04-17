package br.com.todeschini.domain.business.guides.guidemachine.api;

import br.com.todeschini.domain.business.guides.guidemachine.DGuideMachine;

public interface UpdateGuideMachine {

    DGuideMachine update (Long id, DGuideMachine guideMachine);
}
