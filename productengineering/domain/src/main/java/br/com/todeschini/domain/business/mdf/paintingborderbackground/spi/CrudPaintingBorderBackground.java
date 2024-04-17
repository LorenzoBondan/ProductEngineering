package br.com.todeschini.domain.business.mdf.paintingborderbackground.spi;

import br.com.todeschini.domain.SimpleCrud;
import br.com.todeschini.domain.business.mdf.paintingborderbackground.DPaintingBorderBackground;

import java.util.Collection;

public interface CrudPaintingBorderBackground extends SimpleCrud<DPaintingBorderBackground, Long> {

    Collection<? extends DPaintingBorderBackground> findByDescription (String description);
}
