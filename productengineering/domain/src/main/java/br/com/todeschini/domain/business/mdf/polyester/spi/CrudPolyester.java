package br.com.todeschini.domain.business.mdf.polyester.spi;

import br.com.todeschini.domain.SimpleCrud;
import br.com.todeschini.domain.business.mdf.polyester.DPolyester;

import java.util.Collection;

public interface CrudPolyester extends SimpleCrud<DPolyester, Long> {

    Collection<? extends DPolyester> findByDescription (String description);
}
