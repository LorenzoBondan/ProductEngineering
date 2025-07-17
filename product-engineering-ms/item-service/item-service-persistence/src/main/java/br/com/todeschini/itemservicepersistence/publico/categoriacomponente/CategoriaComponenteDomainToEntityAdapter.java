package br.com.todeschini.itemservicepersistence.publico.categoriacomponente;

import br.com.todeschini.itemservicedomain.categoriacomponente.DCategoriaComponente;
import br.com.todeschini.itemservicepersistence.entities.CategoriaComponente;
import br.com.todeschini.libauditdomain.enums.DSituacaoEnum;
import br.com.todeschini.libvalidationhandler.contracts.Convertable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
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
                .situacao(Optional.ofNullable(entity.getSituacao())
                        .map(situacao -> DSituacaoEnum.valueOf(situacao.name()))
                        .orElse(null))
                .build();
    }
}
