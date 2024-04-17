package br.com.todeschini.webapi.rest.packaging.usedpolyethylene;

import br.com.todeschini.domain.business.packaging.usedpolyethylene.DUsedPolyethylene;

public class UsedPolyethyleneFactory {

    public static DUsedPolyethylene createDUsedPolyethylene() {
        DUsedPolyethylene usedPolyethylene = new DUsedPolyethylene();
        usedPolyethylene.setId(1L);
        usedPolyethylene.setPolyethyleneCode(1L);
        usedPolyethylene.setGhostCode("220F99");
        usedPolyethylene.setNetQuantity(1.0);
        usedPolyethylene.setGrossQuantity(1.0);
        usedPolyethylene.setMeasurementUnit("UN");
        return usedPolyethylene;
    }

    public static DUsedPolyethylene createDuplicatedDUsedPolyethylene(Long duplicatedId, String duplicatedGhostCode) {
        DUsedPolyethylene usedPolyethylene = new DUsedPolyethylene();
        usedPolyethylene.setId(2L);
        usedPolyethylene.setPolyethyleneCode(duplicatedId);
        usedPolyethylene.setGhostCode(duplicatedGhostCode);
        usedPolyethylene.setNetQuantity(1.0);
        usedPolyethylene.setGrossQuantity(1.0);
        usedPolyethylene.setMeasurementUnit("UN");
        return usedPolyethylene;
    }

    public static DUsedPolyethylene createNonExistingDUsedPolyethylene(Long nonExistingId) {
        DUsedPolyethylene usedPolyethylene = new DUsedPolyethylene();
        usedPolyethylene.setId(nonExistingId);
        usedPolyethylene.setPolyethyleneCode(1L);
        usedPolyethylene.setGhostCode("220F99");
        usedPolyethylene.setNetQuantity(1.0);
        usedPolyethylene.setGrossQuantity(1.0);
        usedPolyethylene.setMeasurementUnit("UN");
        return usedPolyethylene;
    }
}
