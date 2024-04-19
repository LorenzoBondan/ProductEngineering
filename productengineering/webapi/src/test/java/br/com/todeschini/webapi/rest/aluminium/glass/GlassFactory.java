package br.com.todeschini.webapi.rest.aluminium.glass;

import br.com.todeschini.domain.business.aluminium.glass.DGlass;
import br.com.todeschini.domain.business.publico.color.DColor;

public class GlassFactory {

    public static DGlass createDGlass() {
        DGlass Glass = new DGlass();
        Glass.setCode(1111111L);
        Glass.setDescription("Glass");
        Glass.setMeasure1(1);
        Glass.setMeasure2(1);
        Glass.setMeasure3(1);
        Glass.setMeasurementUnit("UN");
        Glass.setColor(new DColor(1L));
        return Glass;
    }

    public static DGlass createDuplicatedDGlass() {
        DGlass Glass = new DGlass();
        Glass.setCode(2222222L);
        Glass.setDescription("Glass");
        Glass.setMeasure1(1);
        Glass.setMeasure2(1);
        Glass.setMeasure3(1);
        Glass.setMeasurementUnit("UN");
        Glass.setColor(new DColor(1L));
        return Glass;
    }

    public static DGlass createNonExistingDGlass(Long nonExistingId) {
        DGlass Glass = new DGlass();
        Glass.setCode(nonExistingId);
        Glass.setDescription("Glass");
        Glass.setMeasure1(1);
        Glass.setMeasure2(1);
        Glass.setMeasure3(1);
        Glass.setMeasurementUnit("UN");
        Glass.setColor(new DColor(1L));
        return Glass;
    }
}
