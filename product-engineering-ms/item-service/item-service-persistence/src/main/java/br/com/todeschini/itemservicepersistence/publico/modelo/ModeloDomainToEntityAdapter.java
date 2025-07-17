package br.com.todeschini.itemservicepersistence.publico.modelo;

import br.com.todeschini.itemservicedomain.modelo.DModelo;
import br.com.todeschini.itemservicepersistence.entities.Modelo;
import br.com.todeschini.libauditdomain.enums.DSituacaoEnum;
import br.com.todeschini.libvalidationhandler.contracts.Convertable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ModeloDomainToEntityAdapter implements Convertable<Modelo, DModelo> {

    @Override
    public Modelo toEntity(DModelo domain) {
        return Modelo.builder()
                .cdmodelo(domain.getCodigo())
                .descricao(domain.getDescricao())
                .build();
    }

    @Override
    public DModelo toDomain(Modelo entity) {
        return DModelo.builder()
                .codigo(entity.getCdmodelo())
                .descricao(entity.getDescricao())
                .situacao(Optional.ofNullable(entity.getSituacao())
                        .map(situacao -> DSituacaoEnum.valueOf(situacao.name()))
                        .orElse(null))
                .build();
    }
}
