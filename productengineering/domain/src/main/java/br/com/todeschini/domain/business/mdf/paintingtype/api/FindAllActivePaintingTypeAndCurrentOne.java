package br.com.todeschini.domain.business.mdf.paintingtype.api;

import br.com.todeschini.domain.business.mdf.paintingtype.DPaintingType;

import java.util.List;

public interface FindAllActivePaintingTypeAndCurrentOne {

    List<DPaintingType> findAllActiveAndCurrentOne (Long id);
}
