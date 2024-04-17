package br.com.todeschini.domain.business.guides.machinegroup.api;

import br.com.todeschini.domain.business.guides.machinegroup.DMachineGroup;

import java.util.List;

public interface FindAllActiveMachineGroupAndCurrentOne {

    List<DMachineGroup> findAllActiveAndCurrentOne (Long id);
}
