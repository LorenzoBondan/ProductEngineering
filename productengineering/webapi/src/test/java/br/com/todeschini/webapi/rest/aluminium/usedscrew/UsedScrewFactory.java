package br.com.todeschini.webapi.rest.aluminium.usedscrew;

import br.com.todeschini.domain.business.aluminium.usedscrew.DUsedScrew;

public class UsedScrewFactory {

    public static DUsedScrew createDUsedScrew() {
        DUsedScrew usedScrew = new DUsedScrew();
        usedScrew.setId(1L);
        usedScrew.setScrewCode(1L);
        usedScrew.setAluminiumSonCode(220000307L);
        usedScrew.setQuantity(1.0);
        usedScrew.setMeasurementUnit("UN");
        return usedScrew;
    }

    public static DUsedScrew createDuplicatedDUsedScrew(Long duplicatedId) {
        DUsedScrew usedScrew = new DUsedScrew();
        usedScrew.setId(2L);
        usedScrew.setScrewCode(duplicatedId);
        usedScrew.setAluminiumSonCode(duplicatedId);
        usedScrew.setQuantity(1.0);
        usedScrew.setMeasurementUnit("UN");
        return usedScrew;
    }

    public static DUsedScrew createNonExistingDUsedScrew(Long nonExistingId) {
        DUsedScrew usedScrew = new DUsedScrew();
        usedScrew.setId(nonExistingId);
        usedScrew.setScrewCode(1L);
        usedScrew.setAluminiumSonCode(220000307L);
        usedScrew.setQuantity(1.0);
        usedScrew.setMeasurementUnit("UN");
        return usedScrew;
    }
}
