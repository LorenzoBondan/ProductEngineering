package br.com.todeschini.webapi.rest.aluminium.aluminiumtype;

import br.com.todeschini.domain.business.aluminium.aluminiumtype.DAluminiumType;

public class AluminiumTypeFactory {

    public static DAluminiumType createDAluminiumType() {
        DAluminiumType AluminiumType = new DAluminiumType();
        AluminiumType.setId(1L);
        AluminiumType.setName("AluminiumType");
        AluminiumType.setLessQuantity(1.0);
        return AluminiumType;
    }

    public static DAluminiumType createDuplicatedDAluminiumType() {
        DAluminiumType AluminiumType = new DAluminiumType();
        AluminiumType.setId(2L);
        AluminiumType.setName("AluminiumType");
        AluminiumType.setLessQuantity(1.0);
        return AluminiumType;
    }

    public static DAluminiumType createNonExistingDAluminiumType(Long nonExistingId) {
        DAluminiumType AluminiumType = new DAluminiumType();
        AluminiumType.setId(nonExistingId);
        AluminiumType.setName("AluminiumType");
        AluminiumType.setLessQuantity(1.0);
        return AluminiumType;
    }
}
