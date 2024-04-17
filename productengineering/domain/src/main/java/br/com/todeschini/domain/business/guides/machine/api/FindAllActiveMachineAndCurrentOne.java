package br.com.todeschini.domain.business.guides.machine.api;

import br.com.todeschini.domain.business.guides.machine.DMachine;

import java.util.List;

public interface FindAllActiveMachineAndCurrentOne {

    List<DMachine> findAllActiveAndCurrentOne (Long id);
}
