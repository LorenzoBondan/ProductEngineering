package br.com.todeschini.domain.business.mdf.paintingtype.api;

import br.com.todeschini.domain.business.mdf.paintingtype.DPaintingType;

public interface UpdatePaintingType {

    DPaintingType update (Long id, DPaintingType PaintingType);
}
