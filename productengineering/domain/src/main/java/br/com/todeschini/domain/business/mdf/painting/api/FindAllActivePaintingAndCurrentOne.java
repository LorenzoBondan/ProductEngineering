package br.com.todeschini.domain.business.mdf.painting.api;

import br.com.todeschini.domain.business.mdf.painting.DPainting;

import java.util.List;

public interface FindAllActivePaintingAndCurrentOne {

    List<DPainting> findAllActiveAndCurrentOne (Long id);
}
