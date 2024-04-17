package br.com.todeschini.webapi.rest.aluminium.trysquare;

import br.com.todeschini.domain.business.aluminium.trysquare.DTrySquare;

public class TrySquareFactory {

    public static DTrySquare createDTrySquare() {
        DTrySquare TrySquare = new DTrySquare();
        TrySquare.setCode(1L);
        TrySquare.setDescription("TrySquare");
        TrySquare.setMeasure1(1);
        TrySquare.setMeasure2(1);
        TrySquare.setMeasure3(1);
        TrySquare.setMeasurementUnit("UN");
        return TrySquare;
    }

    public static DTrySquare createDuplicatedDTrySquare() {
        DTrySquare TrySquare = new DTrySquare();
        TrySquare.setCode(2L);
        TrySquare.setDescription("TrySquare");
        TrySquare.setMeasure1(1);
        TrySquare.setMeasure2(1);
        TrySquare.setMeasure3(1);
        TrySquare.setMeasurementUnit("UN");
        return TrySquare;
    }

    public static DTrySquare createNonExistingDTrySquare(Long nonExistingId) {
        DTrySquare TrySquare = new DTrySquare();
        TrySquare.setCode(nonExistingId);
        TrySquare.setDescription("TrySquare");
        TrySquare.setMeasure1(1);
        TrySquare.setMeasure2(1);
        TrySquare.setMeasure3(1);
        TrySquare.setMeasurementUnit("UN");
        return TrySquare;
    }
}
