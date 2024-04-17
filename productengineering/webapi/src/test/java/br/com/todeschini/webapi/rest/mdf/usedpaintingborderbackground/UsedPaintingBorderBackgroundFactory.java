package br.com.todeschini.webapi.rest.mdf.usedpaintingborderbackground;

import br.com.todeschini.domain.business.mdf.usedpaintingborderbackground.DUsedPaintingBorderBackground;

public class UsedPaintingBorderBackgroundFactory {

    public static DUsedPaintingBorderBackground createDUsedPaintingBorderBackground() {
        DUsedPaintingBorderBackground usedPaintingBorderBackground = new DUsedPaintingBorderBackground();
        usedPaintingBorderBackground.setId(1L);
        usedPaintingBorderBackground.setPaintingBorderBackgroundCode(1L);
        usedPaintingBorderBackground.setPaintingSonCode(220000307L);
        usedPaintingBorderBackground.setNetQuantity(1.0);
        usedPaintingBorderBackground.setGrossQuantity(1.0);
        usedPaintingBorderBackground.setMeasurementUnit("UN");
        return usedPaintingBorderBackground;
    }

    public static DUsedPaintingBorderBackground createDuplicatedDUsedPaintingBorderBackground(Long duplicatedId) {
        DUsedPaintingBorderBackground usedPaintingBorderBackground = new DUsedPaintingBorderBackground();
        usedPaintingBorderBackground.setId(2L);
        usedPaintingBorderBackground.setPaintingBorderBackgroundCode(duplicatedId);
        usedPaintingBorderBackground.setPaintingSonCode(duplicatedId);
        usedPaintingBorderBackground.setNetQuantity(1.0);
        usedPaintingBorderBackground.setGrossQuantity(1.0);
        usedPaintingBorderBackground.setMeasurementUnit("UN");
        return usedPaintingBorderBackground;
    }

    public static DUsedPaintingBorderBackground createNonExistingDUsedPaintingBorderBackground(Long nonExistingId) {
        DUsedPaintingBorderBackground usedPaintingBorderBackground = new DUsedPaintingBorderBackground();
        usedPaintingBorderBackground.setId(nonExistingId);
        usedPaintingBorderBackground.setPaintingBorderBackgroundCode(1L);
        usedPaintingBorderBackground.setPaintingSonCode(220000307L);
        usedPaintingBorderBackground.setNetQuantity(1.0);
        usedPaintingBorderBackground.setGrossQuantity(1.0);
        usedPaintingBorderBackground.setMeasurementUnit("UN");
        return usedPaintingBorderBackground;
    }
}
