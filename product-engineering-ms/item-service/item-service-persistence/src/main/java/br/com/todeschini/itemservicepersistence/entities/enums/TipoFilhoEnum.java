package br.com.todeschini.itemservicepersistence.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoFilhoEnum {

    MDP(1, "MDP"),
    MDF(2, "MDF"),
    ALUMINIO(3, "Alumínio"),
    FUNDO(4, "Fundo");

    private final Integer value;
    private final String label;

    public static TipoFilhoEnum fromValue(Integer value) {
        for (TipoFilhoEnum obj : TipoFilhoEnum.values()) {
            if (obj.value.equals(value)) {
                return obj;
            }
        }
        throw new IllegalArgumentException("Valor desconhecido: " + value);
    }
}
