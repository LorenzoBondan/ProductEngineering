package br.com.todeschini.domain.business.mdf.usedpainting.spi;

import br.com.todeschini.domain.SimpleCrud;
import br.com.todeschini.domain.business.mdf.usedpainting.DUsedPainting;

import java.util.Collection;

public interface CrudUsedPainting extends SimpleCrud<DUsedPainting, Long> {

    Collection<? extends DUsedPainting> findByPaintingAndPaintingSon (Long paintingId, Long paintingSonId);
}
