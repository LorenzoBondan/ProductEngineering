package br.com.todeschini.domain.business.aluminium.aluminiumtype.spi;

import br.com.todeschini.domain.SimpleCrud;
import br.com.todeschini.domain.business.aluminium.aluminiumtype.DAluminiumType;

import java.util.Collection;

public interface CrudAluminiumType extends SimpleCrud<DAluminiumType, Long> {

    Collection<? extends DAluminiumType> findByName (String name);
}
