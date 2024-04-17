package br.com.todeschini.domain.business.mdf.usedpaintingborderbackground.spi;

import br.com.todeschini.domain.SimpleCrud;
import br.com.todeschini.domain.business.mdf.usedpaintingborderbackground.DUsedPaintingBorderBackground;

import java.util.Collection;

public interface CrudUsedPaintingBorderBackground extends SimpleCrud<DUsedPaintingBorderBackground, Long> {

    Collection<? extends DUsedPaintingBorderBackground> findByPaintingBorderBackgroundAndPaintingSon (Long paintingBorderBackgroundId, Long sonId);
}
