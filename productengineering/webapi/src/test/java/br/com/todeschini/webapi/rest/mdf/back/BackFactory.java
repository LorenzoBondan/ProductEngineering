package br.com.todeschini.webapi.rest.mdf.back;

import br.com.todeschini.domain.business.mdf.back.DBack;

import java.time.LocalDate;

public class BackFactory {

    public static DBack createDBack() {
        DBack Back = new DBack();
        Back.setCode(11111118L);
        Back.setDescription("Back");
        Back.setFamily("abcd");
        Back.setImplementation(LocalDate.of(3000,1,1));
        Back.setLostPercentage(1.0);
        Back.setMeasure1(1);
        Back.setMeasure2(1);
        Back.setThickness(1.0);
        Back.setSuffix(1);
        return Back;
    }

    public static DBack createDuplicatedDBack() {
        DBack Back = new DBack();
        Back.setCode(22222222L);
        Back.setDescription("Back");
        Back.setFamily("abcd");
        Back.setImplementation(LocalDate.of(3000,1,1));
        Back.setLostPercentage(1.0);
        Back.setMeasure1(1);
        Back.setMeasure2(1);
        Back.setThickness(1.0);
        Back.setSuffix(1);
        return Back;
    }

    public static DBack createNonExistingDBack(Long nonExistingId) {
        DBack Back = new DBack();
        Back.setCode(nonExistingId);
        Back.setDescription("Back");
        Back.setFamily("abcd");
        Back.setImplementation(LocalDate.of(3000,1,1));
        Back.setLostPercentage(1.0);
        Back.setMeasure1(1);
        Back.setMeasure2(1);
        Back.setThickness(1.0);
        Back.setSuffix(1);
        return Back;
    }
}
