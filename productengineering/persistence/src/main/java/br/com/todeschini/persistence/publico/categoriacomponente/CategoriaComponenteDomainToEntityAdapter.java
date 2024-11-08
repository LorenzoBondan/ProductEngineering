package br.com.todeschini.persistence.publico.categoriacomponente;

import br.com.todeschini.domain.Convertable;
import br.com.todeschini.domain.business.enums.DSituacaoEnum;
import br.com.todeschini.domain.business.publico.categoriacomponente.DCategoriaComponente;
import br.com.todeschini.domain.metadata.EntityAdapter;
import br.com.todeschini.persistence.entities.publico.CategoriaComponente;
import org.springframework.stereotype.Component;

@Component
@EntityAdapter(entityClass = CategoriaComponente.class)
public class CategoriaComponenteDomainToEntityAdapter implements Convertable<CategoriaComponente, DCategoriaComponente> {

    @Override
    public CategoriaComponente toEntity(DCategoriaComponente domain) {
        return CategoriaComponente.builder()
                .cdcategoriaComponente(domain.getCodigo())
                .descricao(domain.getDescricao())
                .build();
    }

    @Override
    public DCategoriaComponente toDomain(CategoriaComponente entity) {
        return DCategoriaComponente.builder()
                .codigo(entity.getCdcategoriaComponente())
                .descricao(entity.getDescricao())
                .situacao(DSituacaoEnum.valueOf(entity.getSituacao().name()))
                .build();
    }
}
