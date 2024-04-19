package br.com.todeschini.webapi.rest.publico.son;

import br.com.todeschini.domain.business.publico.color.DColor;
import br.com.todeschini.domain.business.publico.son.DSon;

public class SonFactory {

    public static DSon createDSon() {
        DSon son = new DSon();
        son.setCode(111111111L);
        son.setDescription("Son");
        son.setMeasure1(1);
        son.setMeasure2(1);
        son.setMeasure3(1);
        son.setMeasurementUnit("UN");
        son.setColor(new DColor(1L));
        son.setFatherCode(123L);
        return son;
    }

    public static DSon createDuplicatedDSon() {
        DSon son = new DSon();
        son.setCode(2111111111L);
        son.setDescription("Son");
        son.setMeasure1(1);
        son.setMeasure2(1);
        son.setMeasure3(1);
        son.setMeasurementUnit("UN");
        son.setColor(new DColor(1L));
        son.setFatherCode(123L);
        return son;
    }

    public static DSon createNonExistingDSon(Long nonExistingId) {
        DSon son = new DSon();
        son.setCode(nonExistingId);
        son.setDescription("Son");
        son.setMeasure1(1);
        son.setMeasure2(1);
        son.setMeasure3(1);
        son.setMeasurementUnit("UN");
        son.setColor(new DColor(1L));
        son.setFatherCode(123L);
        return son;
    }
}
