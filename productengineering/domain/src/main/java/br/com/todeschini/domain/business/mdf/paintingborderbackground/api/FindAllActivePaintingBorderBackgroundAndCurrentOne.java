package br.com.todeschini.domain.business.mdf.paintingborderbackground.api;

import br.com.todeschini.domain.business.mdf.paintingborderbackground.DPaintingBorderBackground;

import java.util.List;

public interface FindAllActivePaintingBorderBackgroundAndCurrentOne {

    List<DPaintingBorderBackground> findAllActiveAndCurrentOne (Long id);
}
