package br.com.todeschini.domain.business.guides.machine.spi;

import br.com.todeschini.domain.SimpleCrud;
import br.com.todeschini.domain.business.guides.machine.DMachine;

import java.util.Collection;

public interface CrudMachine extends SimpleCrud<DMachine, Long> {

    Collection<? extends DMachine> findByName (String name);
}
