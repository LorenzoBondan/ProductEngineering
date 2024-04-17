package br.com.todeschini.domain.business.mdf.painting.api;

import br.com.todeschini.domain.business.mdf.painting.DPainting;

public interface UpdatePainting {

    DPainting update (Long id, DPainting Painting);
}
