package br.com.todeschini.domain.business.aluminium.usedglass.api;

import br.com.todeschini.domain.business.aluminium.usedglass.DUsedGlass;

import java.util.List;

public interface FindAllActiveUsedGlassAndCurrentOne {

    List<DUsedGlass> findAllActiveAndCurrentOne (Long id);
}
