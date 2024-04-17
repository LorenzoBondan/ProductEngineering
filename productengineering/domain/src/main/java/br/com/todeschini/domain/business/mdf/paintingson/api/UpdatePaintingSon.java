package br.com.todeschini.domain.business.mdf.paintingson.api;

import br.com.todeschini.domain.business.mdf.paintingson.DPaintingSon;

public interface UpdatePaintingSon {

    DPaintingSon update (Long id, DPaintingSon PaintingSon);
}
