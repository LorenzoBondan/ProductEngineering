package br.com.todeschini.webapi.rest.aluminium.usedtrysquare;

import br.com.todeschini.domain.business.aluminium.usedtrysquare.DUsedTrySquare;

public class UsedTrySquareFactory {

    public static DUsedTrySquare createDUsedTrySquare() {
        DUsedTrySquare usedTrySquare = new DUsedTrySquare();
        usedTrySquare.setId(1L);
        usedTrySquare.setTrySquareCode(1L);
        usedTrySquare.setAluminiumSonCode(220000307L);
        usedTrySquare.setQuantity(1.0);
        usedTrySquare.setMeasurementUnit("UN");
        return usedTrySquare;
    }

    public static DUsedTrySquare createDuplicatedDUsedTrySquare(Long duplicatedId) {
        DUsedTrySquare usedTrySquare = new DUsedTrySquare();
        usedTrySquare.setId(2L);
        usedTrySquare.setTrySquareCode(duplicatedId);
        usedTrySquare.setAluminiumSonCode(duplicatedId);
        usedTrySquare.setQuantity(1.0);
        usedTrySquare.setMeasurementUnit("UN");
        return usedTrySquare;
    }

    public static DUsedTrySquare createNonExistingDUsedTrySquare(Long nonExistingId) {
        DUsedTrySquare usedTrySquare = new DUsedTrySquare();
        usedTrySquare.setId(nonExistingId);
        usedTrySquare.setTrySquareCode(1L);
        usedTrySquare.setAluminiumSonCode(220000307L);
        usedTrySquare.setQuantity(1.0);
        usedTrySquare.setMeasurementUnit("UN");
        return usedTrySquare;
    }
}
