package br.com.todeschini.webapi.rest.mdf.painting;

import br.com.todeschini.domain.business.mdf.painting.DPainting;
import br.com.todeschini.domain.business.mdf.paintingtype.DPaintingType;

import java.time.LocalDate;

public class PaintingFactory {

    public static DPainting createDPainting() {
        DPainting Painting = new DPainting();
        Painting.setCode(1111111L);
        Painting.setDescription("Painting");
        Painting.setFamily("abcd");
        Painting.setImplementation(LocalDate.of(3000,1,1));
        Painting.setLostPercentage(1.0);
        Painting.setPaintingType(new DPaintingType(1L));
        return Painting;
    }

    public static DPainting createDuplicatedDPainting(String duplicatedDescription, Long duplicatedId) {
        DPainting Painting = new DPainting();
        Painting.setCode(2222222L);
        Painting.setDescription(duplicatedDescription);
        Painting.setFamily("abcd");
        Painting.setImplementation(LocalDate.of(3000,1,1));
        Painting.setLostPercentage(1.0);
        Painting.setPaintingType(new DPaintingType(duplicatedId));
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
