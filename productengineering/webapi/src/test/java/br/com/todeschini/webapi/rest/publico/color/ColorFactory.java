package br.com.todeschini.webapi.rest.publico.color;

import br.com.todeschini.domain.business.publico.color.DColor;

public class ColorFactory {

    public static DColor createDColor() {
        DColor color = new DColor();
        color.setCode(1L);
        color.setName("Color");
        return color;
    }

    public static DColor createDuplicatedDColor() {
        DColor color = new DColor();
        color.setCode(2L);
        color.setName("Color");
        return color;
    }

    public static DColor createNonExistingDColor(Long nonExistingId) {
        DColor color = new DColor();
        color.setCode(nonExistingId);
        color.setName("Color");
        return color;
    }
}
