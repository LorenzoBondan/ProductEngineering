package br.com.todeschini.webapi.rest.mdp.usededgebanding;

import br.com.todeschini.domain.business.mdp.usededgebanding.DUsedEdgeBanding;

public class UsedEdgeBandingFactory {

    public static DUsedEdgeBanding createDUsedEdgeBanding() {
        DUsedEdgeBanding usedEdgeBanding = new DUsedEdgeBanding();
        usedEdgeBanding.setId(1L);
        usedEdgeBanding.setEdgeBandingCode(1L);
        usedEdgeBanding.setMdpSonCode(220000307L);
        usedEdgeBanding.setNetQuantity(1.0);
        usedEdgeBanding.setGrossQuantity(1.0);
        usedEdgeBanding.setMeasurementUnit("UN");
        return usedEdgeBanding;
    }

    public static DUsedEdgeBanding createDuplicatedDUsedEdgeBanding(Long duplicatedId) {
        DUsedEdgeBanding usedEdgeBanding = new DUsedEdgeBanding();
        usedEdgeBanding.setId(2L);
        usedEdgeBanding.setEdgeBandingCode(duplicatedId);
        usedEdgeBanding.setMdpSonCode(duplicatedId);
        usedEdgeBanding.setNetQuantity(1.0);
        usedEdgeBanding.setGrossQuantity(1.0);
        usedEdgeBanding.setMeasurementUnit("UN");
        return usedEdgeBanding;
    }

    public static DUsedEdgeBanding createNonExistingDUsedEdgeBanding(Long nonExistingId) {
        DUsedEdgeBanding usedEdgeBanding = new DUsedEdgeBanding();
        usedEdgeBanding.setId(nonExistingId);
        usedEdgeBanding.setEdgeBandingCode(1L);
        usedEdgeBanding.setMdpSonCode(220000307L);
        usedEdgeBanding.setNetQuantity(1.0);
        usedEdgeBanding.setGrossQuantity(1.0);
        usedEdgeBanding.setMeasurementUnit("UN");
        return usedEdgeBanding;
    }
}
