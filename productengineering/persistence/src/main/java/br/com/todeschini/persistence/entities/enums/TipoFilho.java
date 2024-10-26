package br.com.todeschini.persistence.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoFilho implements MappableEnum {

    MDP(1, "MDP"),
    MDF(2, "MDF"),
    ALUMINIO(3, "Alumínio"),
    FUNDO(4, "Fundo");

    private final Integer value;
    private final String label;

    public static TipoFilho fromValue(Integer value) {
        for (TipoFilho obj : TipoFilho.values()) {
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
