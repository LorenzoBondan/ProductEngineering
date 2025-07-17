package br.com.todeschini.itemservicepersistence.publico.pai;

import br.com.todeschini.itemservicedomain.enums.DTipoPinturaEnum;
import br.com.todeschini.itemservicedomain.filho.DFilho;
import br.com.todeschini.itemservicedomain.pai.DPai;
import br.com.todeschini.itemservicepersistence.entities.CategoriaComponente;
import br.com.todeschini.itemservicepersistence.entities.Filho;
import br.com.todeschini.itemservicepersistence.entities.Modelo;
import br.com.todeschini.itemservicepersistence.entities.Pai;
import br.com.todeschini.itemservicepersistence.entities.enums.TipoPinturaEnum;
import br.com.todeschini.itemservicepersistence.publico.categoriacomponente.CategoriaComponenteDomainToEntityAdapter;
import br.com.todeschini.itemservicepersistence.publico.filho.FilhoDomainToEntityAdapter;
import br.com.todeschini.itemservicepersistence.publico.modelo.ModeloDomainToEntityAdapter;
import br.com.todeschini.libauditdomain.enums.DSituacaoEnum;
import br.com.todeschini.libvalidationhandler.contracts.Convertable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PaiDomainToEntityAdapter implements Convertable<Pai, DPai> {

    private final FilhoDomainToEntityAdapter filhoDomainToEntityAdapter;
    private final ModeloDomainToEntityAdapter modeloDomainToEntityAdapter;
    private final CategoriaComponenteDomainToEntityAdapter categoriaComponenteDomainToEntityAdapter;

    @Override
    public Pai toEntity(DPai domain) {

        List<Filho> filhos = Optional.ofNullable(domain.getFilhos())
                .map(lista -> lista.stream()
                        .map(filhoDomainToEntityAdapter::toEntity)
                        .collect(Collectors.toList())
                )
                .orElse(new ArrayList<>());

        return Pai.builder()
                .cdpai(domain.getCodigo())
                .modelo(new Modelo(domain.getModelo().getCodigo()))
                .categoriaComponente(new CategoriaComponente(domain.getCategoriaComponente().getCodigo()))
                .descricao(domain.getDescricao())
                .bordasComprimento(domain.getBordasComprimento())
                .bordasLargura(domain.getBordasLargura())
                .numeroCantoneiras(Optional.ofNullable(domain.getNumeroCantoneiras())
                        .orElse(4))
                .tntUmaFace(domain.getTntUmaFace())
                .plasticoAcima(domain.getPlasticoAcima())
                .plasticoAdicional(domain.getPlasticoAdicional())
                .larguraPlastico(domain.getLarguraPlastico())
                .faces(domain.getFaces())
                .especial(domain.getEspecial())
                .tipoPintura(Optional.ofNullable(domain.getTipoPintura())
                        .map(tipoPintura -> TipoPinturaEnum.valueOf(tipoPintura.name()))
                        .orElse(null))

                .filhos(filhos)

                .build();
    }

    @Override
    public DPai toDomain(Pai entity) {

        List<DFilho> filhos = Optional.ofNullable(entity.getFilhos())
                .map(lista -> lista.stream()
                        .map(filhoDomainToEntityAdapter::toDomain)
                        .collect(Collectors.toList())
                )
                .orElse(new ArrayList<>());

        return DPai.builder()
                .codigo(entity.getCdpai())
                .modelo(Optional.ofNullable(entity.getModelo())
                        .map(modeloDomainToEntityAdapter::toDomain)
                        .orElse(null))
                .categoriaComponente(Optional.ofNullable(entity.getCategoriaComponente())
                        .map(categoriaComponenteDomainToEntityAdapter::toDomain)
                        .orElse(null))
                .descricao(entity.getDescricao())
                .bordasComprimento(entity.getBordasComprimento())
                .bordasLargura(entity.getBordasLargura())
                .numeroCantoneiras(Optional.ofNullable(entity.getNumeroCantoneiras())
                        .orElse(4))
                .tntUmaFace(entity.getTntUmaFace())
                .plasticoAcima(entity.getPlasticoAcima())
                .plasticoAdicional(entity.getPlasticoAdicional())
                .larguraPlastico(entity.getLarguraPlastico())
                .faces(entity.getFaces())
                .especial(entity.getEspecial())
                .tipoPintura(Optional.ofNullable(entity.getTipoPintura())
                        .map(tipoPintura -> DTipoPinturaEnum.valueOf(tipoPintura.name()))
                        .orElse(null))
                .situacao(Optional.ofNullable(entity.getSituacao())
                        .map(situacao -> DSituacaoEnum.valueOf(situacao.name()))
                        .orElse(null))

                .filhos(filhos)

                .build();
    }
}
