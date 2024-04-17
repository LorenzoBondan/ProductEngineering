package br.com.todeschini.webapi.rest.mdp.usedglue;

import br.com.todeschini.domain.business.mdp.usedglue.DUsedGlue;

public class UsedGlueFactory {

    public static DUsedGlue createDUsedGlue() {
        DUsedGlue usedGlue = new DUsedGlue();
        usedGlue.setId(1L);
        usedGlue.setGlueCode(1L);
        usedGlue.setMdpSonCode(220000307L);
        usedGlue.setNetQuantity(1.0);
        usedGlue.setGrossQuantity(1.0);
        usedGlue.setMeasurementUnit("UN");
        return usedGlue;
    }

    public static DUsedGlue createDuplicatedDUsedGlue(Long duplicatedId) {
        DUsedGlue usedGlue = new DUsedGlue();
        usedGlue.setId(2L);
        usedGlue.setGlueCode(duplicatedId);
        usedGlue.setMdpSonCode(duplicatedId);
        usedGlue.setNetQuantity(1.0);
        usedGlue.setGrossQuantity(1.0);
        usedGlue.setMeasurementUnit("UN");
        return usedGlue;
    }

    public static DUsedGlue createNonExistingDUsedGlue(Long nonExistingId) {
        DUsedGlue usedGlue = new DUsedGlue();
        usedGlue.setId(nonExistingId);
        usedGlue.setGlueCode(1L);
        usedGlue.setMdpSonCode(220000307L);
        usedGlue.setNetQuantity(1.0);
        usedGlue.setGrossQuantity(1.0);
        usedGlue.setMeasurementUnit("UN");
        return usedGlue;
    }
}
