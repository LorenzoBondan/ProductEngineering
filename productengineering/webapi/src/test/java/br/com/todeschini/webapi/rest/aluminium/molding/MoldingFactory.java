package br.com.todeschini.webapi.rest.aluminium.molding;

import br.com.todeschini.domain.business.aluminium.molding.DMolding;

public class MoldingFactory {

    public static DMolding createDMolding() {
        DMolding Molding = new DMolding();
        Molding.setCode(1L);
        Molding.setDescription("Molding");
        Molding.setMeasure1(1);
        Molding.setMeasure2(1);
        Molding.setMeasure3(1);
        Molding.setMeasurementUnit("UN");
        return Molding;
    }

    public static DMolding createDuplicatedDMolding() {
        DMolding Molding = new DMolding();
        Molding.setCode(2L);
        Molding.setDescription("Molding");
        Molding.setMeasure1(1);
        Molding.setMeasure2(1);
        Molding.setMeasure3(1);
        Molding.setMeasurementUnit("UN");
        return Molding;
    }

    public static DMolding createNonExistingDMolding(Long nonExistingId) {
        DMolding Molding = new DMolding();
        Molding.setCode(nonExistingId);
        Molding.setDescription("Molding");
        Molding.setMeasure1(1);
        Molding.setMeasure2(1);
        Molding.setMeasure3(1);
        Molding.setMeasurementUnit("UN");
        return Molding;
    }
}
