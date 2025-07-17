package br.com.todeschini.lixeiraservicedomain.lixeira;

import br.com.todeschini.libauditdomain.enums.DSituacaoEnum;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DLixeira {

    @EqualsAndHashCode.Include
    private Integer id;
    private String nometabela;
    private LocalDateTime data;
    private Map<String, Object> entidadeid;
    private String usuario;
    private DSituacaoEnum situacao;
    private String tabela;
}
