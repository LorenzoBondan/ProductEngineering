package br.com.todeschini.webapi.rest.mdf.polyester;

import br.com.todeschini.domain.business.mdf.polyester.DPolyester;

import java.time.LocalDate;

public class PolyesterFactory {

    public static DPolyester createDPolyester() {
        DPolyester Polyester = new DPolyester();
        Polyester.setCode(1111111L);
        Polyester.setDescription("Polyester");
        Polyester.setFamily("abcd");
        Polyester.setImplementation(LocalDate.of(3000,1,1));
        Polyester.setLostPercentage(1.0);
        return Polyester;
    }

    public static DPolyester createDuplicatedDPolyester() {
        DPolyester Polyester = new DPolyester();
        Polyester.setCode(2222222L);
        Polyester.setDescription("Polyester");
        Polyester.setFamily("abcd");
        Polyester.setImplementation(LocalDate.of(3000,1,1));
        Polyester.setLostPercentage(1.0);
        return Polyester;
    }

    public static DPolyester createNonExistingDPolyester(Long nonExistingId) {
        DPolyester Polyester = new DPolyester();
        Polyester.setCode(nonExistingId);
        Polyester.setDescription("Polyester");
        Polyester.setFamily("abcd");
        Polyester.setImplementation(LocalDate.of(3000,1,1));
        Polyester.setLostPercentage(1.0);
        return Polyester;
    }
}
