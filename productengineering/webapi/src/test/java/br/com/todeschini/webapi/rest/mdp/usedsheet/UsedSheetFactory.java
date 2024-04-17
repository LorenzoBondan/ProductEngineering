package br.com.todeschini.webapi.rest.mdp.usedsheet;

import br.com.todeschini.domain.business.mdp.usedsheet.DUsedSheet;

public class UsedSheetFactory {

    public static DUsedSheet createDUsedSheet() {
        DUsedSheet usedSheet = new DUsedSheet();
        usedSheet.setId(1L);
        usedSheet.setSheetCode(1L);
        usedSheet.setMdpSonCode(220000307L);
        usedSheet.setNetQuantity(1.0);
        usedSheet.setGrossQuantity(1.0);
        usedSheet.setMeasurementUnit("UN");
        return usedSheet;
    }

    public static DUsedSheet createDuplicatedDUsedSheet(Long duplicatedId) {
        DUsedSheet usedSheet = new DUsedSheet();
        usedSheet.setId(2L);
        usedSheet.setSheetCode(duplicatedId);
        usedSheet.setMdpSonCode(duplicatedId);
        usedSheet.setNetQuantity(1.0);
        usedSheet.setGrossQuantity(1.0);
        usedSheet.setMeasurementUnit("UN");
        return usedSheet;
    }

    public static DUsedSheet createNonExistingDUsedSheet(Long nonExistingId) {
        DUsedSheet usedSheet = new DUsedSheet();
        usedSheet.setId(nonExistingId);
        usedSheet.setSheetCode(1L);
        usedSheet.setMdpSonCode(220000307L);
        usedSheet.setNetQuantity(1.0);
        usedSheet.setGrossQuantity(1.0);
        usedSheet.setMeasurementUnit("UN");
        return usedSheet;
    }
}
