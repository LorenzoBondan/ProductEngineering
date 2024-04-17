package br.com.todeschini.domain.business.mdf.usedpainting.api;

import br.com.todeschini.domain.business.mdf.usedpainting.DUsedPainting;

public interface UpdateUsedPainting {

    DUsedPainting update (Long id, DUsedPainting usedPainting);
}
