package br.com.todeschini.webapi.rest.aluminium.usedglass;

import br.com.todeschini.domain.business.aluminium.usedglass.DUsedGlass;

public class UsedGlassFactory {

    public static DUsedGlass createDUsedGlass() {
        DUsedGlass usedGlass = new DUsedGlass();
        usedGlass.setId(1L);
        usedGlass.setGlassCode(1L);
        usedGlass.setAluminiumSonCode(220000307L);
        usedGlass.setQuantity(1.0);
        usedGlass.setMeasurementUnit("UN");
        return usedGlass;
    }

    public static DUsedGlass createDuplicatedDUsedGlass(Long duplicatedId) {
        DUsedGlass usedGlass = new DUsedGlass();
        usedGlass.setId(2L);
        usedGlass.setGlassCode(duplicatedId);
        usedGlass.setAluminiumSonCode(duplicatedId);
        usedGlass.setQuantity(1.0);
        usedGlass.setMeasurementUnit("UN");
        return usedGlass;
    }

    public static DUsedGlass createNonExistingDUsedGlass(Long nonExistingId) {
        DUsedGlass usedGlass = new DUsedGlass();
        usedGlass.setId(nonExistingId);
        usedGlass.setGlassCode(1L);
        usedGlass.setAluminiumSonCode(220000307L);
        usedGlass.setQuantity(1.0);
        usedGlass.setMeasurementUnit("UN");
        return usedGlass;
    }
}
