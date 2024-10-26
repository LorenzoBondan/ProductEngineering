package br.com.todeschini.domain.business.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DTipoFilho {

    MDP(1, "MDP"),
    MDF(2, "MDF"),
    ALUMINIO(3, "Alumínio"),
    FUNDO(4, "Fundo");

    private final Integer value;
    private final String label;

    public static DTipoFilho fromValue(Integer value) {
        for (DTipoFilho obj : DTipoFilho.values()) {
            if (obj.value.equals(value)) {
                return obj;
            }
        }
        throw new IllegalArgumentException("Valor desconhecido: " + value);
    }
}
