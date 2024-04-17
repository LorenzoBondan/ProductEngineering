package br.com.todeschini.webapi.rest.mdf.painting;

import br.com.todeschini.domain.business.mdf.painting.DPainting;
import br.com.todeschini.domain.business.mdf.paintingtype.DPaintingType;

import java.time.LocalDate;

public class PaintingFactory {

    public static DPainting createDPainting() {
        DPainting Painting = new DPainting();
        Painting.setCode(1L);
        Painting.setDescription("Painting");
        Painting.setFamily("abcd");
        Painting.setImplementation(LocalDate.of(3000,1,1));
        Painting.setLostPercentage(1.0);
        Painting.setPaintingType(new DPaintingType(1L));
        return Painting;
    }

    public static DPainting createDuplicatedDPainting() {
        DPainting Painting = new DPainting();
        Painting.setCode(2L);
        Painting.setDescription("Painting");
        Painting.setFamily("abcd");
        Painting.setImplementation(LocalDate.of(3000,1,1));
        Painting.setLostPercentage(1.0);
        Painting.setPaintingType(new DPaintingType(1L));
        return Painting;
    }

    public static DPainting createNonExistingDPainting(Long nonExistingId) {
        DPainting Painting = new DPainting();
        Painting.setCode(nonExistingId);
        Painting.setDescription("Painting");
        Painting.setFamily("abcd");
        Painting.setImplementation(LocalDate.of(3000,1,1));
        Painting.setLostPercentage(1.0);
        Painting.setPaintingType(new DPaintingType(1L));
        return Painting;
    }
}
