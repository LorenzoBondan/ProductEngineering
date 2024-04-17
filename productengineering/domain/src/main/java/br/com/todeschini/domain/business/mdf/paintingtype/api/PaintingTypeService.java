package br.com.todeschini.domain.business.mdf.paintingtype.api;

import org.springframework.stereotype.Component;

@Component
public interface PaintingTypeService extends FindPaintingType, InsertPaintingType, UpdatePaintingType, DeletePaintingType, InactivatePaintingType,
        FindAllActivePaintingTypeAndCurrentOne {
}
