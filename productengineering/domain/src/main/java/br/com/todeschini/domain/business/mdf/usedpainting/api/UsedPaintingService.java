package br.com.todeschini.domain.business.mdf.usedpainting.api;

import org.springframework.stereotype.Component;

@Component
public interface UsedPaintingService extends FindUsedPainting, InsertUsedPainting, UpdateUsedPainting, DeleteUsedPainting, InactivateUsedPainting,
        FindAllActiveUsedPaintingAndCurrentOne {
}
