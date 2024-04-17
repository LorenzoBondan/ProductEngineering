package br.com.todeschini.domain.business.publico.color.spi;

import br.com.todeschini.domain.SimpleCrud;
import br.com.todeschini.domain.business.publico.color.DColor;

import java.util.Collection;

public interface CrudColor extends SimpleCrud<DColor, Long> {

    Collection<? extends DColor> findByName (String name);
}
