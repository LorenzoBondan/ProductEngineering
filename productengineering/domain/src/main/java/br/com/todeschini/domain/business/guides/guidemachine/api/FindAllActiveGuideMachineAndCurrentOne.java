package br.com.todeschini.domain.business.guides.guidemachine.api;

import br.com.todeschini.domain.business.guides.guidemachine.DGuideMachine;

import java.util.List;

public interface FindAllActiveGuideMachineAndCurrentOne {

    List<DGuideMachine> findAllActiveAndCurrentOne (Long id);
}
