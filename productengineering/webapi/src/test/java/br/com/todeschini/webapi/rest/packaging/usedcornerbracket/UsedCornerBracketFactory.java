package br.com.todeschini.webapi.rest.packaging.usedcornerbracket;

import br.com.todeschini.domain.business.packaging.usedcornerbracket.DUsedCornerBracket;

public class UsedCornerBracketFactory {

    public static DUsedCornerBracket createDUsedCornerBracket() {
        DUsedCornerBracket usedCornerBracket = new DUsedCornerBracket();
        usedCornerBracket.setId(1L);
        usedCornerBracket.setCornerBracketCode(1L);
        usedCornerBracket.setGhostCode("220F99");
        usedCornerBracket.setNetQuantity(1.0);
        usedCornerBracket.setGrossQuantity(1.0);
        usedCornerBracket.setMeasurementUnit("UN");
        return usedCornerBracket;
    }

    public static DUsedCornerBracket createDuplicatedDUsedCornerBracket(Long duplicatedId, String duplicatedGhostCode) {
        DUsedCornerBracket usedCornerBracket = new DUsedCornerBracket();
        usedCornerBracket.setId(2L);
        usedCornerBracket.setCornerBracketCode(duplicatedId);
        usedCornerBracket.setGhostCode(duplicatedGhostCode);
        usedCornerBracket.setNetQuantity(1.0);
        usedCornerBracket.setGrossQuantity(1.0);
        usedCornerBracket.setMeasurementUnit("UN");
        return usedCornerBracket;
    }

    public static DUsedCornerBracket createNonExistingDUsedCornerBracket(Long nonExistingId) {
        DUsedCornerBracket usedCornerBracket = new DUsedCornerBracket();
        usedCornerBracket.setId(nonExistingId);
        usedCornerBracket.setCornerBracketCode(1L);
        usedCornerBracket.setGhostCode("220F99");
        usedCornerBracket.setNetQuantity(1.0);
        usedCornerBracket.setGrossQuantity(1.0);
        usedCornerBracket.setMeasurementUnit("UN");
        return usedCornerBracket;
    }
}
