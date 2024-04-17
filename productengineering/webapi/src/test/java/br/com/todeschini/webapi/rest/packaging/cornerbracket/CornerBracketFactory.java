package br.com.todeschini.webapi.rest.packaging.cornerbracket;

import br.com.todeschini.domain.business.packaging.cornerbracket.DCornerBracket;

import java.time.LocalDate;

public class CornerBracketFactory {

    public static DCornerBracket createDCornerBracket() {
        DCornerBracket cornerBracket = new DCornerBracket();
        cornerBracket.setCode(1L);
        cornerBracket.setDescription("CornerBracket");
        cornerBracket.setFamily("abcd");
        cornerBracket.setImplementation(LocalDate.of(3000,1,1));
        cornerBracket.setLostPercentage(1.0);
        return cornerBracket;
    }

    public static DCornerBracket createDuplicatedDCornerBracket() {
        DCornerBracket cornerBracket = new DCornerBracket();
        cornerBracket.setCode(2L);
        cornerBracket.setDescription("CornerBracket");
        cornerBracket.setFamily("abcd");
        cornerBracket.setImplementation(LocalDate.of(3000,1,1));
        cornerBracket.setLostPercentage(1.0);
        return cornerBracket;
    }

    public static DCornerBracket createNonExistingDCornerBracket(Long nonExistingId) {
        DCornerBracket cornerBracket = new DCornerBracket();
        cornerBracket.setCode(nonExistingId);
        cornerBracket.setDescription("CornerBracket");
        cornerBracket.setFamily("abcd");
        cornerBracket.setImplementation(LocalDate.of(3000,1,1));
        cornerBracket.setLostPercentage(1.0);
        return cornerBracket;
    }
}
