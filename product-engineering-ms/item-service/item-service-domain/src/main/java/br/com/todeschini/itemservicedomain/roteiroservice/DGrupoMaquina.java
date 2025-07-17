package br.com.todeschini.itemservicedomain.roteiroservice;

import br.com.todeschini.libauditdomain.enums.DSituacaoEnum;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DGrupoMaquina {

    @EqualsAndHashCode.Include
    private Integer codigo;
    private String nome;
    private DSituacaoEnum situacao;

    public DGrupoMaquina(Integer codigo){
        this.codigo = codigo;
    }
}
