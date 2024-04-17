package br.com.todeschini.webapi.rest.aluminium.useddrawerpull;

import br.com.todeschini.domain.business.aluminium.useddrawerpull.DUsedDrawerPull;

public class UsedDrawerPullFactory {

    public static DUsedDrawerPull createDUsedDrawerPull() {
        DUsedDrawerPull usedDrawerPull = new DUsedDrawerPull();
        usedDrawerPull.setId(1L);
        usedDrawerPull.setDrawerPullCode(1L);
        usedDrawerPull.setAluminiumSonCode(220000307L);
        usedDrawerPull.setQuantity(1.0);
        usedDrawerPull.setMeasurementUnit("UN");
        return usedDrawerPull;
    }

    public static DUsedDrawerPull createDuplicatedDUsedDrawerPull(Long duplicatedId) {
        DUsedDrawerPull usedDrawerPull = new DUsedDrawerPull();
        usedDrawerPull.setId(2L);
        usedDrawerPull.setDrawerPullCode(duplicatedId);
        usedDrawerPull.setAluminiumSonCode(duplicatedId);
        usedDrawerPull.setQuantity(1.0);
        usedDrawerPull.setMeasurementUnit("UN");
        return usedDrawerPull;
    }

    public static DUsedDrawerPull createNonExistingDUsedDrawerPull(Long nonExistingId) {
        DUsedDrawerPull usedDrawerPull = new DUsedDrawerPull();
        usedDrawerPull.setId(nonExistingId);
        usedDrawerPull.setDrawerPullCode(1L);
        usedDrawerPull.setAluminiumSonCode(220000307L);
        usedDrawerPull.setQuantity(1.0);
        usedDrawerPull.setMeasurementUnit("UN");
        return usedDrawerPull;
    }
}
