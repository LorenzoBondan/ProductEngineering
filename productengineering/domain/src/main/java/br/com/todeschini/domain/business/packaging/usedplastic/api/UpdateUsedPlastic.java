package br.com.todeschini.domain.business.packaging.usedplastic.api;

import br.com.todeschini.domain.business.packaging.usedplastic.DUsedPlastic;

public interface UpdateUsedPlastic {

    DUsedPlastic update (Long id, DUsedPlastic usedPlastic);
}
