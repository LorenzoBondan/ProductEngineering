package br.com.todeschini.webapi.rest.mdf.usedpolyester;

import br.com.todeschini.domain.business.mdf.usedpolyester.DUsedPolyester;

public class UsedPolyesterFactory {

    public static DUsedPolyester createDUsedPolyester() {
        DUsedPolyester usedPolyester = new DUsedPolyester();
        usedPolyester.setId(1L);
        usedPolyester.setPolyesterCode(1L);
        usedPolyester.setPaintingSonCode(220000307L);
        usedPolyester.setNetQuantity(1.0);
        usedPolyester.setGrossQuantity(1.0);
        usedPolyester.setMeasurementUnit("UN");
        return usedPolyester;
    }

    public static DUsedPolyester createDuplicatedDUsedPolyester(Long duplicatedId) {
        DUsedPolyester usedPolyester = new DUsedPolyester();
        usedPolyester.setId(2L);
        usedPolyester.setPolyesterCode(duplicatedId);
        usedPolyester.setPaintingSonCode(duplicatedId);
        usedPolyester.setNetQuantity(1.0);
        usedPolyester.setGrossQuantity(1.0);
        usedPolyester.setMeasurementUnit("UN");
        return usedPolyester;
    }

    public static DUsedPolyester createNonExistingDUsedPolyester(Long nonExistingId) {
        DUsedPolyester usedPolyester = new DUsedPolyester();
        usedPolyester.setId(nonExistingId);
        usedPolyester.setPolyesterCode(1L);
        usedPolyester.setPaintingSonCode(220000307L);
        usedPolyester.setNetQuantity(1.0);
        usedPolyester.setGrossQuantity(1.0);
        usedPolyester.setMeasurementUnit("UN");
        return usedPolyester;
    }
}
