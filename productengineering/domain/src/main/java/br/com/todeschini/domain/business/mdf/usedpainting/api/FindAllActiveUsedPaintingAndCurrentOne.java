package br.com.todeschini.domain.business.mdf.usedpainting.api;

import br.com.todeschini.domain.business.mdf.usedpainting.DUsedPainting;

import java.util.List;

public interface FindAllActiveUsedPaintingAndCurrentOne {

    List<DUsedPainting> findAllActiveAndCurrentOne (Long id);
}
