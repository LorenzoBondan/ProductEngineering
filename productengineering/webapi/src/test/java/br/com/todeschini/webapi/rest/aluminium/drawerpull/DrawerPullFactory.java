package br.com.todeschini.webapi.rest.aluminium.drawerpull;

import br.com.todeschini.domain.business.aluminium.drawerpull.DDrawerPull;

public class DrawerPullFactory {

    public static DDrawerPull createDDrawerPull() {
        DDrawerPull DrawerPull = new DDrawerPull();
        DrawerPull.setCode(1L);
        DrawerPull.setDescription("DrawerPull");
        DrawerPull.setMeasure1(1);
        DrawerPull.setMeasure2(1);
        DrawerPull.setMeasure3(1);
        DrawerPull.setMeasurementUnit("UN");
        return DrawerPull;
    }

    public static DDrawerPull createDuplicatedDDrawerPull() {
        DDrawerPull DrawerPull = new DDrawerPull();
        DrawerPull.setCode(2L);
        DrawerPull.setDescription("DrawerPull");
        DrawerPull.setMeasure1(1);
        DrawerPull.setMeasure2(1);
        DrawerPull.setMeasure3(1);
        DrawerPull.setMeasurementUnit("UN");
        return DrawerPull;
    }

    public static DDrawerPull createNonExistingDDrawerPull(Long nonExistingId) {
        DDrawerPull DrawerPull = new DDrawerPull();
        DrawerPull.setCode(nonExistingId);
        DrawerPull.setDescription("DrawerPull");
        DrawerPull.setMeasure1(1);
        DrawerPull.setMeasure2(1);
        DrawerPull.setMeasure3(1);
        DrawerPull.setMeasurementUnit("UN");
        return DrawerPull;
    }
}
