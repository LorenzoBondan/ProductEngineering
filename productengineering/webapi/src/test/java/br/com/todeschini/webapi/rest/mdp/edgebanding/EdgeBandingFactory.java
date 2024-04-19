package br.com.todeschini.webapi.rest.mdp.edgebanding;

import br.com.todeschini.domain.business.mdp.edgebanding.DEdgeBanding;
import br.com.todeschini.domain.business.publico.color.DColor;

import java.time.LocalDate;

public class EdgeBandingFactory {

    public static DEdgeBanding createDEdgeBanding() {
        DEdgeBanding edgeBanding = new DEdgeBanding();
        edgeBanding.setCode(1111111L);
        edgeBanding.setDescription("EdgeBanding");
        edgeBanding.setFamily("abcd");
        edgeBanding.setImplementation(LocalDate.of(3000,1,1));
        edgeBanding.setLostPercentage(1.0);
        edgeBanding.setHeight(1);
        edgeBanding.setThickness(1);
        edgeBanding.setColor(new DColor(1L));
        return edgeBanding;
    }

    public static DEdgeBanding createDuplicatedDEdgeBanding() {
        DEdgeBanding edgeBanding = new DEdgeBanding();
        edgeBanding.setCode(2222222L);
        edgeBanding.setDescription("EdgeBanding");
        edgeBanding.setFamily("abcd");
        edgeBanding.setImplementation(LocalDate.of(3000,1,1));
        edgeBanding.setLostPercentage(1.0);
        edgeBanding.setHeight(1);
        edgeBanding.setThickness(1);
        edgeBanding.setColor(new DColor(1L));
        return edgeBanding;
    }

    public static DEdgeBanding createNonExistingDEdgeBanding(Long nonExistingId) {
        DEdgeBanding edgeBanding = new DEdgeBanding();
        edgeBanding.setCode(nonExistingId);
        edgeBanding.setDescription("EdgeBanding");
        edgeBanding.setFamily("abcd");
        edgeBanding.setImplementation(LocalDate.of(3000,1,1));
        edgeBanding.setLostPercentage(1.0);
        edgeBanding.setHeight(1);
        edgeBanding.setThickness(1);
        edgeBanding.setColor(new DColor(1L));
        return edgeBanding;
    }
}
