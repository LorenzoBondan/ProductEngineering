package br.com.todeschini.domain.business.aluminium.glass.api;

import br.com.todeschini.domain.business.aluminium.glass.DGlass;

import java.util.List;

public interface FindAllActiveGlassAndCurrentOne {

    List<DGlass> findAllActiveAndCurrentOne (Long id);
}
