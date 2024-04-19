package br.com.todeschini.webapi.rest.aluminium.screw;

import br.com.todeschini.domain.business.aluminium.screw.DScrew;

public class ScrewFactory {

    public static DScrew createDScrew() {
        DScrew Screw = new DScrew();
        Screw.setCode(1111111L);
        Screw.setDescription("Screw");
        Screw.setMeasure1(1);
        Screw.setMeasure2(1);
        Screw.setMeasure3(1);
        Screw.setMeasurementUnit("UN");
        return Screw;
    }

    public static DScrew createDuplicatedDScrew() {
        DScrew Screw = new DScrew();
        Screw.setCode(2222222L);
        Screw.setDescription("Screw");
        Screw.setMeasure1(1);
        Screw.setMeasure2(1);
        Screw.setMeasure3(1);
        Screw.setMeasurementUnit("UN");
        return Screw;
    }

    public static DScrew createNonExistingDScrew(Long nonExistingId) {
        DScrew Screw = new DScrew();
        Screw.setCode(nonExistingId);
        Screw.setDescription("Screw");
        Screw.setMeasure1(1);
        Screw.setMeasure2(1);
        Screw.setMeasure3(1);
        Screw.setMeasurementUnit("UN");
        return Screw;
    }
}
