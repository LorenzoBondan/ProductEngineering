package br.com.todeschini.itemservicedomain.roteiroservice;

import br.com.todeschini.libauditdomain.enums.DSituacaoEnum;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DMaquina {

    @EqualsAndHashCode.Include
    private Integer codigo;
    private String nome;
    private String formula;
    private Double valor;
    private DGrupoMaquina grupoMaquina;
    private DSituacaoEnum situacao;

    public DMaquina(Integer codigo){
        this.codigo = codigo;
    }
}
