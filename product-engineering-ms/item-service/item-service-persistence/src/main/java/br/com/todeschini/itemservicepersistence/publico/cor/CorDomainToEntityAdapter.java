package br.com.todeschini.itemservicepersistence.publico.cor;

import br.com.todeschini.itemservicedomain.cor.DCor;
import br.com.todeschini.itemservicepersistence.entities.Cor;
import br.com.todeschini.libauditdomain.enums.DSituacaoEnum;
import br.com.todeschini.libvalidationhandler.contracts.Convertable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CorDomainToEntityAdapter implements Convertable<Cor, DCor> {

    @Override
    public Cor toEntity(DCor domain) {
        return Cor.builder()
                .cdcor(domain.getCodigo())
                .descricao(domain.getDescricao())
                .hexa(domain.getHexa())
                .build();
    }

    @Override
    public DCor toDomain(Cor entity) {
        return DCor.builder()
                .codigo(entity.getCdcor())
                .descricao(entity.getDescricao())
                .hexa(entity.getHexa())
                .situacao(Optional.ofNullable(entity.getSituacao())
                        .map(situacao -> DSituacaoEnum.valueOf(situacao.name()))
                        .orElse(null))
                .build();
    }
}
