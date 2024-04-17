package br.com.todeschini.domain.business.aluminium.usedglass.spi;

import br.com.todeschini.domain.SimpleCrud;
import br.com.todeschini.domain.business.aluminium.usedglass.DUsedGlass;

import java.util.Collection;

public interface CrudUsedGlass extends SimpleCrud<DUsedGlass, Long> {

    Collection<? extends DUsedGlass> findByGlassAndAluminiumSon (Long GlassId, Long aluminiumSonId);
}
