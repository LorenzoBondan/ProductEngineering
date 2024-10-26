package br.com.todeschini.domain.business.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DTipoPintura {

    ACETINADA(1, "Acetinada"),
    ALTO_BRILHO(2, "Alto brilho"),
    ACETINADA_VIDRO(3, "Acetinada vidro");

    private final Integer value;
    private final String label;

    public static DTipoPintura fromValue(Integer value) {
        for (DTipoPintura obj : DTipoPintura.values()) {
            if (obj.value.equals(value)) {
                return obj;
            }
        }
        throw new IllegalArgumentException("Valor desconhecido: " + value);
    }
}
