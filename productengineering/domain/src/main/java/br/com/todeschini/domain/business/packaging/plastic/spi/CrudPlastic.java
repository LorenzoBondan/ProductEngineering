package br.com.todeschini.domain.business.packaging.plastic.spi;

import br.com.todeschini.domain.SimpleCrud;
import br.com.todeschini.domain.business.packaging.plastic.DPlastic;

import java.util.Collection;

public interface CrudPlastic extends SimpleCrud<DPlastic, Long> {

    Collection<? extends DPlastic> findByDescription (String description);
}
