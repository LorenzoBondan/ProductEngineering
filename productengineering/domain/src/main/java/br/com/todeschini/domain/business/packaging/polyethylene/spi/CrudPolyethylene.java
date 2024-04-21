package br.com.todeschini.domain.business.packaging.polyethylene.spi;

import br.com.todeschini.domain.SimpleCrud;
import br.com.todeschini.domain.business.packaging.polyethylene.DPolyethylene;

import java.util.Collection;

public interface CrudPolyethylene extends SimpleCrud<DPolyethylene, Long> {

    Collection<? extends DPolyethylene> findByDescription (String description);
}
