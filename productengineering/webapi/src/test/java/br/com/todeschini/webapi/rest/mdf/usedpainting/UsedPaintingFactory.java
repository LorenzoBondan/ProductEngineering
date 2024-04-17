package br.com.todeschini.webapi.rest.mdf.usedpainting;

import br.com.todeschini.domain.business.mdf.usedpainting.DUsedPainting;

public class UsedPaintingFactory {

    public static DUsedPainting createDUsedPainting() {
        DUsedPainting usedPainting = new DUsedPainting();
        usedPainting.setId(1L);
        usedPainting.setPaintingCode(1L);
        usedPainting.setPaintingSonCode(220000307L);
        usedPainting.setNetQuantity(1.0);
        usedPainting.setGrossQuantity(1.0);
        usedPainting.setMeasurementUnit("UN");
        return usedPainting;
    }

    public static DUsedPainting createDuplicatedDUsedPainting(Long duplicatedId) {
        DUsedPainting usedPainting = new DUsedPainting();
        usedPainting.setId(2L);
        usedPainting.setPaintingCode(duplicatedId);
        usedPainting.setPaintingSonCode(duplicatedId);
        usedPainting.setNetQuantity(1.0);
        usedPainting.setGrossQuantity(1.0);
        usedPainting.setMeasurementUnit("UN");
        return usedPainting;
    }

    public static DUsedPainting createNonExistingDUsedPainting(Long nonExistingId) {
        DUsedPainting usedPainting = new DUsedPainting();
        usedPainting.setId(nonExistingId);
        usedPainting.setPaintingCode(1L);
        usedPainting.setPaintingSonCode(220000307L);
        usedPainting.setNetQuantity(1.0);
        usedPainting.setGrossQuantity(1.0);
        usedPainting.setMeasurementUnit("UN");
        return usedPainting;
    }
}
