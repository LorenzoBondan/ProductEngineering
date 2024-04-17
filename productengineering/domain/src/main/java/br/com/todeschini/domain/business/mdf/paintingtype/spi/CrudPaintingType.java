package br.com.todeschini.domain.business.mdf.paintingtype.spi;

import br.com.todeschini.domain.SimpleCrud;
import br.com.todeschini.domain.business.mdf.paintingtype.DPaintingType;

import java.util.Collection;

public interface CrudPaintingType extends SimpleCrud<DPaintingType, Long> {

    Collection<? extends DPaintingType> findByDescription (String description);
}
