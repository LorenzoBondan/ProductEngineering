package br.com.todeschini.webapi.rest.mdp.mdpson;

import br.com.todeschini.domain.business.mdp.mdpson.DMDPSon;
import br.com.todeschini.domain.business.publico.color.DColor;
import br.com.todeschini.domain.business.publico.son.DSon;

public class MDPSonFactory {

    public static DMDPSon createDMDPSon() {
        DMDPSon mdpSon = new DMDPSon();
        mdpSon.setCode(111111111L);
        mdpSon.setDescription("Son");
        mdpSon.setMeasure1(1);
        mdpSon.setMeasure2(1);
        mdpSon.setMeasure3(1);
        mdpSon.setMeasurementUnit("UN");
        mdpSon.setColor(new DColor(1L));
        mdpSon.setFatherCode(123L);
        mdpSon.setGuide(null);
        return mdpSon;
    }

    public static DMDPSon createDuplicatedDMDPSon() {
        DMDPSon mdpSon = new DMDPSon();
        mdpSon.setCode(211111111L);
        mdpSon.setDescription("Son");
        mdpSon.setMeasure1(1);
        mdpSon.setMeasure2(1);
        mdpSon.setMeasure3(1);
        mdpSon.setMeasurementUnit("UN");
        mdpSon.setColor(new DColor(1L));
        mdpSon.setFatherCode(123L);
        mdpSon.setGuide(null);
        return mdpSon;
    }

    public static DMDPSon createNonExistingDMDPSon(Long nonExistingId) {
        DMDPSon mdpSon = new DMDPSon();
        mdpSon.setCode(nonExistingId);
        mdpSon.setDescription("Son");
        mdpSon.setMeasure1(1);
        mdpSon.setMeasure2(1);
        mdpSon.setMeasure3(1);
        mdpSon.setMeasurementUnit("UN");
        mdpSon.setColor(new DColor(1L));
        mdpSon.setFatherCode(123L);
        mdpSon.setGuide(null);
        return mdpSon;
    }
}
