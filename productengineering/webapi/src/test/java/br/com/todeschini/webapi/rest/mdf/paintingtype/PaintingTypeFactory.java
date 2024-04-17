package br.com.todeschini.webapi.rest.mdf.paintingtype;

import br.com.todeschini.domain.business.mdf.paintingtype.DPaintingType;

public class PaintingTypeFactory {

    public static DPaintingType createDPaintingType() {
        DPaintingType PaintingType = new DPaintingType();
        PaintingType.setId(1L);
        PaintingType.setDescription("PaintingType");
        return PaintingType;
    }

    public static DPaintingType createDuplicatedDPaintingType() {
        DPaintingType PaintingType = new DPaintingType();
        PaintingType.setId(2L);
        PaintingType.setDescription("PaintingType");
        return PaintingType;
    }

    public static DPaintingType createNonExistingDPaintingType(Long nonExistingId) {
        DPaintingType PaintingType = new DPaintingType();
        PaintingType.setId(nonExistingId);
        PaintingType.setDescription("PaintingType");
        return PaintingType;
    }
}
