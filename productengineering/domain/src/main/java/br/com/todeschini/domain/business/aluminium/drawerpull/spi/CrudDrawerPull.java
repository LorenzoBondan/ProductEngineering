package br.com.todeschini.domain.business.aluminium.drawerpull.spi;

import br.com.todeschini.domain.SimpleCrud;
import br.com.todeschini.domain.business.aluminium.drawerpull.DDrawerPull;

import java.util.Collection;

public interface CrudDrawerPull extends SimpleCrud<DDrawerPull, Long> {

    Collection<? extends DDrawerPull> findByDescription (String description);
}
