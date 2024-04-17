package br.com.todeschini.domain.business.mdf.usedpaintingborderbackground.api;

import org.springframework.stereotype.Component;

@Component
public interface UsedPaintingBorderBackgroundService extends FindUsedPaintingBorderBackground, InsertUsedPaintingBorderBackground, UpdateUsedPaintingBorderBackground, DeleteUsedPaintingBorderBackground, InactivateUsedPaintingBorderBackground,
        FindAllActiveUsedPaintingBorderBackgroundAndCurrentOne {
}
