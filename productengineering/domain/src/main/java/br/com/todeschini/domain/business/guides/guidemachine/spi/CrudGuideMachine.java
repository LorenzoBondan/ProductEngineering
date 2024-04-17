package br.com.todeschini.domain.business.guides.guidemachine.spi;

import br.com.todeschini.domain.SimpleCrud;
import br.com.todeschini.domain.business.guides.guidemachine.DGuideMachine;

import java.util.Collection;

public interface CrudGuideMachine extends SimpleCrud<DGuideMachine, Long> {

    Collection<? extends DGuideMachine> findByGuideAndMachine (Long guideId, Long machineId);
}
