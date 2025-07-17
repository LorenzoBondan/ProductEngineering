package br.com.todeschini.itemservicepersistence.publico.medidas;

import br.com.todeschini.itemservicedomain.medidas.DMedidas;
import br.com.todeschini.itemservicepersistence.entities.Medidas;
import br.com.todeschini.libauditdomain.enums.DSituacaoEnum;
import br.com.todeschini.libvalidationhandler.contracts.Convertable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MedidasDomainToEntityAdapter implements Convertable<Medidas, DMedidas> {

    @Override
    public Medidas toEntity(DMedidas domain) {
        return Medidas.builder()
                .cdmedidas(domain.getCodigo())
                .altura(domain.getAltura())
                .largura(domain.getLargura())
                .espessura(domain.getEspessura())
                .build();
    }

    @Override
    public DMedidas toDomain(Medidas entity) {
        return DMedidas.builder()
                .codigo(entity.getCdmedidas())
                .altura(entity.getAltura())
                .largura(entity.getLargura())
                .espessura(entity.getEspessura())
                .situacao(Optional.ofNullable(entity.getSituacao())
                        .map(situacao -> DSituacaoEnum.valueOf(situacao.name()))
                        .orElse(null))
                .build();
    }
}
