package br.com.todeschini.domain.business.guides.machinegroup.spi;

import br.com.todeschini.domain.SimpleCrud;
import br.com.todeschini.domain.business.guides.machinegroup.DMachineGroup;

import java.util.Collection;

public interface CrudMachineGroup extends SimpleCrud<DMachineGroup, Long> {

    Collection<? extends DMachineGroup> findByName (String name);
}
