package br.com.todeschini.webapi.rest.packaging.usednonwovenfabric;

import br.com.todeschini.domain.business.packaging.usednonwovenfabric.DUsedNonwovenFabric;

public class UsedNonwovenFabricFactory {

    public static DUsedNonwovenFabric createDUsedNonwovenFabric() {
        DUsedNonwovenFabric usedNonwovenFabric = new DUsedNonwovenFabric();
        usedNonwovenFabric.setId(1L);
        usedNonwovenFabric.setNonwovenFabricCode(1L);
        usedNonwovenFabric.setGhostCode("220F99");
        usedNonwovenFabric.setNetQuantity(1.0);
        usedNonwovenFabric.setGrossQuantity(1.0);
        usedNonwovenFabric.setMeasurementUnit("UN");
        return usedNonwovenFabric;
    }

    public static DUsedNonwovenFabric createDuplicatedDUsedNonwovenFabric(Long duplicatedId, String duplicatedGhostCode) {
        DUsedNonwovenFabric usedNonwovenFabric = new DUsedNonwovenFabric();
        usedNonwovenFabric.setId(2L);
        usedNonwovenFabric.setNonwovenFabricCode(duplicatedId);
        usedNonwovenFabric.setGhostCode(duplicatedGhostCode);
        usedNonwovenFabric.setNetQuantity(1.0);
        usedNonwovenFabric.setGrossQuantity(1.0);
        usedNonwovenFabric.setMeasurementUnit("UN");
        return usedNonwovenFabric;
    }

    public static DUsedNonwovenFabric createNonExistingDUsedNonwovenFabric(Long nonExistingId) {
        DUsedNonwovenFabric usedNonwovenFabric = new DUsedNonwovenFabric();
        usedNonwovenFabric.setId(nonExistingId);
        usedNonwovenFabric.setNonwovenFabricCode(1L);
        usedNonwovenFabric.setGhostCode("220F99");
        usedNonwovenFabric.setNetQuantity(1.0);
        usedNonwovenFabric.setGrossQuantity(1.0);
        usedNonwovenFabric.setMeasurementUnit("UN");
        return usedNonwovenFabric;
    }
}
