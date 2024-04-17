package br.com.todeschini.domain.business.mdf.usedpolyester.spi;

import br.com.todeschini.domain.SimpleCrud;
import br.com.todeschini.domain.business.mdf.usedpolyester.DUsedPolyester;

import java.util.Collection;

public interface CrudUsedPolyester extends SimpleCrud<DUsedPolyester, Long> {

    Collection<? extends DUsedPolyester> findByPolyesterAndPaintingSon (Long polyesterId, Long sonId);
}
