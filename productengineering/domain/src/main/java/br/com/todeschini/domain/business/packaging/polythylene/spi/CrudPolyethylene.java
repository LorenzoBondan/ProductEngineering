package br.com.todeschini.domain.business.packaging.polythylene.spi;

import br.com.todeschini.domain.SimpleCrud;
import br.com.todeschini.domain.business.packaging.polythylene.DPolyethylene;

import java.util.Collection;

public interface CrudPolyethylene extends SimpleCrud<DPolyethylene, Long> {

    Collection<? extends DPolyethylene> findByDescription (String description);
}
