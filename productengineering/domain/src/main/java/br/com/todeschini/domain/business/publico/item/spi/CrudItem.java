package br.com.todeschini.domain.business.publico.item.spi;

import br.com.todeschini.domain.SimpleCrud;
import br.com.todeschini.domain.business.publico.item.DItem;

import java.util.Collection;

public interface CrudItem extends SimpleCrud<DItem, Long> {

    Collection<? extends DItem> findByDescription (String description);
}
