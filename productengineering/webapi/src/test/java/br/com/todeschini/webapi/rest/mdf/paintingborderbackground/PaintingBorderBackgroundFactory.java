package br.com.todeschini.webapi.rest.mdf.paintingborderbackground;

import br.com.todeschini.domain.business.mdf.paintingborderbackground.DPaintingBorderBackground;

import java.time.LocalDate;

public class PaintingBorderBackgroundFactory {

    public static DPaintingBorderBackground createDPaintingBorderBackground() {
        DPaintingBorderBackground paintingBorderBackground = new DPaintingBorderBackground();
        paintingBorderBackground.setCode(1L);
        paintingBorderBackground.setDescription("Back");
        paintingBorderBackground.setFamily("abcd");
        paintingBorderBackground.setImplementation(LocalDate.of(3000,1,1));
        paintingBorderBackground.setLostPercentage(1.0);
        return paintingBorderBackground;
    }

    public static DPaintingBorderBackground createDuplicatedDPaintingBorderBackground() {
        DPaintingBorderBackground paintingBorderBackground = new DPaintingBorderBackground();
        paintingBorderBackground.setCode(2L);
        paintingBorderBackground.setDescription("Back");
        paintingBorderBackground.setFamily("abcd");
        paintingBorderBackground.setImplementation(LocalDate.of(3000,1,1));
        paintingBorderBackground.setLostPercentage(1.0);
        return paintingBorderBackground;
    }

    public static DPaintingBorderBackground createNonExistingDPaintingBorderBackground(Long nonExistingId) {
        DPaintingBorderBackground paintingBorderBackground = new DPaintingBorderBackground();
        paintingBorderBackground.setCode(nonExistingId);
        paintingBorderBackground.setDescription("Back");
        paintingBorderBackground.setFamily("abcd");
        paintingBorderBackground.setImplementation(LocalDate.of(3000,1,1));
        paintingBorderBackground.setLostPercentage(1.0);
        return paintingBorderBackground;
    }
}
