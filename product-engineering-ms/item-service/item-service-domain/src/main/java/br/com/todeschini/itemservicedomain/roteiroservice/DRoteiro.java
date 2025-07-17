package br.com.todeschini.itemservicedomain.roteiroservice;

import br.com.todeschini.libauditdomain.enums.DSituacaoEnum;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DRoteiro {

    @EqualsAndHashCode.Include
    private Integer codigo;
    private String descricao;
    private LocalDate implantacao;
    private LocalDate dataFinal;
    private Double valor;
    private DSituacaoEnum situacao;

    private List<DRoteiroMaquina> roteiroMaquinas = new ArrayList<>();

    public DRoteiro(Integer codigo){
        this.codigo = codigo;
    }

    public Double calcularValor() {
        double valor = 0;
        for(DRoteiroMaquina roteiroMaquina : this.roteiroMaquinas) {
            valor += roteiroMaquina.calcularValorMaquina();
            valor += roteiroMaquina.calcularValorMaquina();
        }
        this.valor = valor;
        return valor;
    }
}
