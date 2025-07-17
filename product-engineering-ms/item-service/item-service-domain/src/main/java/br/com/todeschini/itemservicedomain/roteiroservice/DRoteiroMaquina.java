package br.com.todeschini.itemservicedomain.roteiroservice;

import br.com.todeschini.libauditdomain.enums.DSituacaoEnum;
import br.com.todeschini.libvalidationhandler.util.FormulaCalculadora;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DRoteiroMaquina {

    @EqualsAndHashCode.Include
    private Integer codigo;
    private DRoteiro roteiro;
    private DMaquina maquina;
    private Double tempoMaquina;
    private Double tempoHomem;
    private String unidadeMedida;
    private DSituacaoEnum situacao;

    public DRoteiroMaquina(Integer codigo){
        this.codigo = codigo;
    }

    public void calcularTempo(Integer medida1, Integer medida2, Integer medida3){
        double time = FormulaCalculadora.calcularFormulaCustomizada(
                maquina.getFormula(),
                List.of(medida1, medida2, medida3)
        );
        this.tempoMaquina = Math.round(time * 1e3) / 1e3;
        this.tempoHomem = tempoMaquina;
    }

    public Double calcularValorMaquina() {
        return Math.round(maquina.getValor() * this.tempoMaquina * 1e2) / 1e2;
    }

    public Double calcularValorHomem() {
        return Math.round(maquina.getValor() * this.tempoHomem * 1e2) / 1e2;
    }
}
