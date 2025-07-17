package br.com.todeschini.itemservicepersistence.publico.materialusado;

import br.com.todeschini.itemservicedomain.filho.DFilho;
import br.com.todeschini.itemservicedomain.materialusado.DMaterialUsado;
import br.com.todeschini.itemservicepersistence.entities.Filho;
import br.com.todeschini.itemservicepersistence.entities.MaterialUsado;
import br.com.todeschini.itemservicepersistence.publico.cor.CorDomainToEntityAdapter;
import br.com.todeschini.itemservicepersistence.publico.material.MaterialDomainToEntityAdapter;
import br.com.todeschini.itemservicepersistence.publico.medidas.MedidasDomainToEntityAdapter;
import br.com.todeschini.libauditdomain.enums.DSituacaoEnum;
import br.com.todeschini.libvalidationhandler.contracts.Convertable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MaterialUsadoDomainToEntityAdapter implements Convertable<MaterialUsado, DMaterialUsado> {

    private final MaterialDomainToEntityAdapter materialDomainToEntityAdapter;
    private final MedidasDomainToEntityAdapter medidasDomainToEntityAdapter;
    private final CorDomainToEntityAdapter corDomainToEntityAdapter;

    @Override
    public MaterialUsado toEntity(DMaterialUsado domain) {
        return MaterialUsado.builder()
                .cdmaterialUsado(domain.getCodigo())
                .filho(new Filho(
                        domain.getFilho().getCodigo(),
                        corDomainToEntityAdapter.toEntity(domain.getFilho().getCor()),
                        medidasDomainToEntityAdapter.toEntity(domain.getFilho().getMedidas())
                ))
                .material(Optional.ofNullable(domain.getMaterial())
                        .map(materialDomainToEntityAdapter::toEntity)
                        .orElse(null))
                .quantidadeLiquida(domain.getQuantidadeLiquida())
                .quantidadeBruta(domain.getQuantidadeBruta())
                .valor(domain.calcularValor())
                .unidadeMedida(domain.getUnidadeMedida())
                .build();
    }

    @Override
    public DMaterialUsado toDomain(MaterialUsado entity) {
        return DMaterialUsado.builder()
                .codigo(entity.getCdmaterialUsado())
                .filho(Optional.ofNullable(entity.getFilho())
                        .map(filho -> new DFilho(
                                filho.getCdfilho(),
                                filho.getDescricao(),
                                corDomainToEntityAdapter.toDomain(entity.getFilho().getCor()),
                                medidasDomainToEntityAdapter.toDomain(entity.getFilho().getMedidas())
                                )) // para não gerar stackoverflow
                        .orElse(null))
                .material(Optional.ofNullable(entity.getMaterial())
                        .map(materialDomainToEntityAdapter::toDomain)
                        .orElse(null))
                .quantidadeLiquida(entity.getQuantidadeLiquida())
                .quantidadeBruta(entity.getQuantidadeBruta())
                .valor(entity.getValor())
                .unidadeMedida(entity.getUnidadeMedida())
                .situacao(Optional.ofNullable(entity.getSituacao())
                        .map(situacao -> DSituacaoEnum.valueOf(situacao.name()))
                        .orElse(null))
                .build();
    }
}
