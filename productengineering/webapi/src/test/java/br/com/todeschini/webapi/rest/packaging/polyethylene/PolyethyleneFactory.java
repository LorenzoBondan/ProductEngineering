package br.com.todeschini.webapi.rest.packaging.polyethylene;

import br.com.todeschini.domain.business.packaging.polythylene.DPolyethylene;

import java.time.LocalDate;

public class PolyethyleneFactory {

    public static DPolyethylene createDPolyethylene() {
        DPolyethylene polyethylene = new DPolyethylene();
        polyethylene.setCode(1111111L);
        polyethylene.setDescription("Polyethylene");
        polyethylene.setFamily("abcd");
        polyethylene.setImplementation(LocalDate.of(3000,1,1));
        polyethylene.setLostPercentage(1.0);
        return polyethylene;
    }

    public static DPolyethylene createDuplicatedDPolyethylene() {
        DPolyethylene polyethylene = new DPolyethylene();
        polyethylene.setCode(2222222L);
        polyethylene.setDescription("Polyethylene");
        polyethylene.setFamily("abcd");
        polyethylene.setImplementation(LocalDate.of(3000,1,1));
        polyethylene.setLostPercentage(1.0);
        return polyethylene;
    }

    public static DPolyethylene createNonExistingDPolyethylene(Long nonExistingId) {
        DPolyethylene polyethylene = new DPolyethylene();
        polyethylene.setCode(nonExistingId);
        polyethylene.setDescription("Polyethylene");
        polyethylene.setFamily("abcd");
        polyethylene.setImplementation(LocalDate.of(3000,1,1));
        polyethylene.setLostPercentage(1.0);
        return polyethylene;
    }
}
