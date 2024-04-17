package br.com.todeschini.domain.business.mdf.paintingson.spi;

import br.com.todeschini.domain.SimpleCrud;
import br.com.todeschini.domain.business.mdf.paintingson.DPaintingSon;

import java.util.Collection;

public interface CrudPaintingSon extends SimpleCrud<DPaintingSon, Long> {

    Collection<? extends DPaintingSon> findByDescription (String description);
}
