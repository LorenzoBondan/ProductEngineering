package br.com.todeschini.webapi.rest.guides.guide;

import br.com.todeschini.domain.business.guides.guide.DGuide;

import java.time.LocalDate;

public class GuideFactory {

    public static DGuide createDGuide() {
        DGuide Guide = new DGuide();
        Guide.setId(1L);
        Guide.setImplementation(LocalDate.of(3000,1,1));
        Guide.setFinalDate(LocalDate.of(3000,1,1));
        return Guide;
    }

    public static DGuide createDuplicatedDGuide() {
        DGuide Guide = new DGuide();
        Guide.setId(2L);
        Guide.setImplementation(LocalDate.of(3000,1,1));
        Guide.setFinalDate(LocalDate.of(3000,1,1));
        return Guide;
    }

    public static DGuide createNonExistingDGuide(Long nonExistingId) {
        DGuide Guide = new DGuide();
        Guide.setId(nonExistingId);
        Guide.setImplementation(LocalDate.of(3000,1,1));
        Guide.setFinalDate(LocalDate.of(3000,1,1));
        return Guide;
    }
}
