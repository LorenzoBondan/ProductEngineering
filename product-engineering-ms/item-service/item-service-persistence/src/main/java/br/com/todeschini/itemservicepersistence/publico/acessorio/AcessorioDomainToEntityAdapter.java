package br.com.todeschini.itemservicepersistence.publico.acessorio;

import br.com.todeschini.itemservicedomain.acessorio.DAcessorio;
import br.com.todeschini.itemservicepersistence.entities.Acessorio;
import br.com.todeschini.itemservicepersistence.entities.Cor;
import br.com.todeschini.itemservicepersistence.entities.Medidas;
import br.com.todeschini.itemservicepersistence.publico.cor.CorDomainToEntityAdapter;
import br.com.todeschini.itemservicepersistence.publico.medidas.MedidasDomainToEntityAdapter;
import br.com.todeschini.libauditdomain.enums.DSituacaoEnum;
import br.com.todeschini.libvalidationhandler.contracts.Convertable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AcessorioDomainToEntityAdapter implements Convertable<Acessorio, DAcessorio> {

    private final CorDomainToEntityAdapter corDomainToEntityAdapter;
    private final MedidasDomainToEntityAdapter medidasDomainToEntityAdapter;

    @Override
    public Acessorio toEntity(DAcessorio domain) {
        return Acessorio.builder()
                .cdacessorio(domain.getCodigo())
                .descricao(domain.getDescricao())
                .medidas(Optional.ofNullable(domain.getMedidas())
                        .map(medidas -> new Medidas(medidas.getCodigo()))
                        .orElse(null))
                .cor(Optional.ofNullable(domain.getCor())
                        .map(cor -> new Cor(cor.getCodigo()))
                        .orElse(null))
                .implantacao(domain.getImplantacao())
                .valor(domain.getValor())
                .build();
    }

    @Override
    public DAcessorio toDomain(Acessorio entity) {
        return DAcessorio.builder()
                .codigo(entity.getCdacessorio())
                .descricao(entity.getDescricao())
                .medidas(Optional.ofNullable(entity.getMedidas())
                        .map(medidasDomainToEntityAdapter::toDomain)
                        .orElse(null))
                .cor(Optional.ofNullable(entity.getCor())
                        .map(corDomainToEntityAdapter::toDomain)
                        .orElse(null))
                .implantacao(entity.getImplantacao())
                .valor(entity.getValor())
                .situacao(Optional.ofNullable(entity.getSituacao())
                        .map(situacao -> DSituacaoEnum.valueOf(situacao.name()))
                        .orElse(null))
                .build();
    }
}
