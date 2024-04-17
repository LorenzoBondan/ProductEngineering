package br.com.todeschini.webapi.rest.packaging.usedplastic;

import br.com.todeschini.domain.business.packaging.usedplastic.DUsedPlastic;

public class UsedPlasticFactory {

    public static DUsedPlastic createDUsedPlastic() {
        DUsedPlastic usedPlastic = new DUsedPlastic();
        usedPlastic.setId(1L);
        usedPlastic.setPlasticCode(1L);
        usedPlastic.setGhostCode("220F99");
        usedPlastic.setNetQuantity(1.0);
        usedPlastic.setGrossQuantity(1.0);
        usedPlastic.setMeasurementUnit("UN");
        return usedPlastic;
    }

    public static DUsedPlastic createDuplicatedDUsedPlastic(Long duplicatedId, String duplicatedGhostCode) {
        DUsedPlastic usedPlastic = new DUsedPlastic();
        usedPlastic.setId(2L);
        usedPlastic.setPlasticCode(duplicatedId);
        usedPlastic.setGhostCode(duplicatedGhostCode);
        usedPlastic.setNetQuantity(1.0);
        usedPlastic.setGrossQuantity(1.0);
        usedPlastic.setMeasurementUnit("UN");
        return usedPlastic;
    }

    public static DUsedPlastic createNonExistingDUsedPlastic(Long nonExistingId) {
        DUsedPlastic usedPlastic = new DUsedPlastic();
        usedPlastic.setId(nonExistingId);
        usedPlastic.setPlasticCode(1L);
        usedPlastic.setGhostCode("220F99");
        usedPlastic.setNetQuantity(1.0);
        usedPlastic.setGrossQuantity(1.0);
        usedPlastic.setMeasurementUnit("UN");
        return usedPlastic;
    }
}
