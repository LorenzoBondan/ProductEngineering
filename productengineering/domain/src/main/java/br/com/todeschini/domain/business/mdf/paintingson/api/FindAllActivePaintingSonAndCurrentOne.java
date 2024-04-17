package br.com.todeschini.domain.business.mdf.paintingson.api;

import br.com.todeschini.domain.business.mdf.paintingson.DPaintingSon;

import java.util.List;

public interface FindAllActivePaintingSonAndCurrentOne {

    List<DPaintingSon> findAllActiveAndCurrentOne (Long id);
}
