package br.com.todeschini.itemservicepersistence.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoPinturaEnum {

    ACETINADA(1, "Acetinada"),
    ALTO_BRILHO(2, "Alto brilho"),
    ACETINADA_VIDRO(3, "Acetinada vidro");

    private final Integer value;
    private final String label;

    public static TipoPinturaEnum fromValue(Integer value) {
        for (TipoPinturaEnum obj : TipoPinturaEnum.values()) {
            if (obj.value.equals(value)) {
                return obj;
            }
        }
        throw new IllegalArgumentException("Valor desconhecido: " + value);
    }
}
