package br.com.todeschini.persistence.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoPintura implements MappableEnum {

    ACETINADA(1, "Acetinada"),
    ALTO_BRILHO(2, "Alto brilho"),
    ACETINADA_VIDRO(3, "Acetinada vidro");

    private final Integer value;
    private final String label;

    public static TipoPintura fromValue(Integer value) {
        for (TipoPintura obj : TipoPintura.values()) {
            if (obj.value.equals(value)) {
                return obj;
            }
        }
        throw new IllegalArgumentException("Valor desconhecido: " + value);
    }

    @Override
    public int getValue() {
        return value;
    }
}
