package br.com.todeschini.webapi.rest.aluminium.usedmolding;

import br.com.todeschini.domain.business.aluminium.usedmolding.DUsedMolding;

public class UsedMoldingFactory {

    public static DUsedMolding createDUsedMolding() {
        DUsedMolding usedMolding = new DUsedMolding();
        usedMolding.setId(1L);
        usedMolding.setMoldingCode(1L);
        usedMolding.setAluminiumSonCode(220000307L);
        usedMolding.setQuantity(1.0);
        usedMolding.setMeasurementUnit("UN");
        return usedMolding;
    }

    public static DUsedMolding createDuplicatedDUsedMolding(Long duplicatedId) {
        DUsedMolding usedMolding = new DUsedMolding();
        usedMolding.setId(2L);
        usedMolding.setMoldingCode(duplicatedId);
        usedMolding.setAluminiumSonCode(duplicatedId);
        usedMolding.setQuantity(1.0);
        usedMolding.setMeasurementUnit("UN");
        return usedMolding;
    }

    public static DUsedMolding createNonExistingDUsedMolding(Long nonExistingId) {
        DUsedMolding usedMolding = new DUsedMolding();
        usedMolding.setId(nonExistingId);
        usedMolding.setMoldingCode(1L);
        usedMolding.setAluminiumSonCode(220000307L);
        usedMolding.setQuantity(1.0);
        usedMolding.setMeasurementUnit("UN");
        return usedMolding;
    }
}
