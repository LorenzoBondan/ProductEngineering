package br.com.todeschini.domain.business.mdf.back.spi;

import br.com.todeschini.domain.SimpleCrud;
import br.com.todeschini.domain.business.mdf.back.DBack;

import java.util.Collection;

public interface CrudBack extends SimpleCrud<DBack, Long> {

    Collection<? extends DBack> findByDescription (String description);
}
