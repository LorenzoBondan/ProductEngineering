package br.com.todeschini.webapi.rest.mdf.usedbacksheet;

import br.com.todeschini.domain.business.mdf.usedbacksheet.DUsedBackSheet;

public class UsedBackSheetFactory {

    public static DUsedBackSheet createDUsedBackSheet() {
        DUsedBackSheet usedBackSheet = new DUsedBackSheet();
        usedBackSheet.setId(1L);
        usedBackSheet.setBackCode(1L);
        usedBackSheet.setSheetCode(220000307L);
        usedBackSheet.setNetQuantity(1.0);
        usedBackSheet.setGrossQuantity(1.0);
        usedBackSheet.setMeasurementUnit("UN");
        return usedBackSheet;
    }

    public static DUsedBackSheet createDuplicatedDUsedBackSheet(Long duplicatedId) {
        DUsedBackSheet usedBackSheet = new DUsedBackSheet();
        usedBackSheet.setId(2L);
        usedBackSheet.setBackCode(duplicatedId);
        usedBackSheet.setSheetCode(duplicatedId);
        usedBackSheet.setNetQuantity(1.0);
        usedBackSheet.setGrossQuantity(1.0);
        usedBackSheet.setMeasurementUnit("UN");
        return usedBackSheet;
    }

    public static DUsedBackSheet createNonExistingDUsedBackSheet(Long nonExistingId) {
        DUsedBackSheet usedBackSheet = new DUsedBackSheet();
        usedBackSheet.setId(nonExistingId);
        usedBackSheet.setBackCode(1L);
        usedBackSheet.setSheetCode(220000307L);
        usedBackSheet.setNetQuantity(1.0);
        usedBackSheet.setGrossQuantity(1.0);
        usedBackSheet.setMeasurementUnit("UN");
        return usedBackSheet;
    }
}
