package br.com.todeschini.domain.business.mdf.painting.spi;

import br.com.todeschini.domain.SimpleCrud;
import br.com.todeschini.domain.business.mdf.painting.DPainting;

import java.util.Collection;

public interface CrudPainting extends SimpleCrud<DPainting, Long> {

    Collection<? extends DPainting> findByDescription (String description);
}
