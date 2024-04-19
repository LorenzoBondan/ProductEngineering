package br.com.todeschini.webapi.rest.aluminium.aluminiumson;

import br.com.todeschini.domain.business.aluminium.aluminiumson.DAluminiumSon;
import br.com.todeschini.domain.business.aluminium.aluminiumtype.DAluminiumType;
import br.com.todeschini.domain.business.publico.color.DColor;

public class AluminiumSonFactory {

    public static DAluminiumSon createDAluminiumSon() {
        DAluminiumSon son = new DAluminiumSon();
        son.setCode(111111111L);
        son.setDescription("Son");
        son.setMeasure1(1);
        son.setMeasure2(1);
        son.setMeasure3(1);
        son.setMeasurementUnit("UN");
        son.setColor(new DColor(1L));
        son.setFatherCode(123L);
        son.setAluminiumType(new DAluminiumType(1L));
        return son;
    }

    public static DAluminiumSon createDuplicatedDAluminiumSon() {
        DAluminiumSon son = new DAluminiumSon();
        son.setCode(222222222L);
        son.setDescription("Son");
        son.setMeasure1(1);
        son.setMeasure2(1);
        son.setMeasure3(1);
        son.setMeasurementUnit("UN");
        son.setColor(new DColor(1L));
        son.setFatherCode(123L);
        son.setAluminiumType(new DAluminiumType(1L));
        return son;
    }

    public static DAluminiumSon createNonExistingDAluminiumSon(Long nonExistingId) {
        DAluminiumSon son = new DAluminiumSon();
        son.setCode(nonExistingId);
        son.setDescription("Son");
        son.setMeasure1(1);
        son.setMeasure2(1);
        son.setMeasure3(1);
        son.setMeasurementUnit("UN");
        son.setColor(new DColor(1L));
        son.setFatherCode(123L);
        son.setAluminiumType(new DAluminiumType(1L));
        return son;
    }
}
