package br.com.todeschini.domain.business.aluminium.aluminiumson.spi;

import br.com.todeschini.domain.SimpleCrud;
import br.com.todeschini.domain.business.aluminium.aluminiumson.DAluminiumSon;

import java.util.Collection;

public interface CrudAluminiumSon extends SimpleCrud<DAluminiumSon, Long> {

    Collection<? extends DAluminiumSon> findByDescription (String description);
}
