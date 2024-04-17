package br.com.todeschini.webapi.rest.publico.father;

import br.com.todeschini.domain.business.publico.color.DColor;
import br.com.todeschini.domain.business.publico.father.DFather;

public class FatherFactory {

    public static DFather createDFather() {
        DFather father = new DFather();
        father.setCode(1L);
        father.setDescription("Father");
        father.setMeasure1(1);
        father.setMeasure2(1);
        father.setMeasure3(1);
        father.setMeasurementUnit("UN");
        father.setColor(new DColor(1L));
        return father;
    }

    public static DFather createDuplicatedDFather() {
        DFather father = new DFather();
        father.setCode(2L);
        father.setDescription("Father");
        father.setMeasure1(1);
        father.setMeasure2(1);
        father.setMeasure3(1);
        father.setMeasurementUnit("UN");
        father.setColor(new DColor(1L));
        return father;
    }

    public static DFather createNonExistingDFather(Long nonExistingId) {
        DFather father = new DFather();
        father.setCode(nonExistingId);
        father.setDescription("Father");
        father.setMeasure1(1);
        father.setMeasure2(1);
        father.setMeasure3(1);
        father.setMeasurementUnit("UN");
        father.setColor(new DColor(1L));
        return father;
    }
}
