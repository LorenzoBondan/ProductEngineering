package br.com.todeschini.domain.business.mdf.usedpaintingborderbackground.api;

import br.com.todeschini.domain.business.mdf.usedpaintingborderbackground.DUsedPaintingBorderBackground;

import java.util.List;

public interface FindAllActiveUsedPaintingBorderBackgroundAndCurrentOne {

    List<DUsedPaintingBorderBackground> findAllActiveAndCurrentOne (Long id);
}
