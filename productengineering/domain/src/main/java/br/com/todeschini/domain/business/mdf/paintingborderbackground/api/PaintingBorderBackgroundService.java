package br.com.todeschini.domain.business.mdf.paintingborderbackground.api;

import org.springframework.stereotype.Component;

@Component
public interface PaintingBorderBackgroundService extends FindPaintingBorderBackground, InsertPaintingBorderBackground, UpdatePaintingBorderBackground, DeletePaintingBorderBackground, InactivatePaintingBorderBackground,
        FindAllActivePaintingBorderBackgroundAndCurrentOne {
}
