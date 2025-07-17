package br.com.todeschini.itemservicepersistence.publico.filho;

import br.com.todeschini.itemservicedomain.acessoriousado.DAcessorioUsado;
import br.com.todeschini.itemservicedomain.enums.DTipoFilhoEnum;
import br.com.todeschini.itemservicedomain.enums.DTipoPinturaEnum;
import br.com.todeschini.itemservicedomain.filho.DFilho;
import br.com.todeschini.itemservicedomain.materialusado.DMaterialUsado;
import br.com.todeschini.itemservicedomain.pai.DPai;
import br.com.todeschini.itemservicedomain.roteiroservice.DRoteiro;
import br.com.todeschini.itemservicepersistence.entities.AcessorioUsado;
import br.com.todeschini.itemservicepersistence.entities.Filho;
import br.com.todeschini.itemservicepersistence.entities.MaterialUsado;
import br.com.todeschini.itemservicepersistence.entities.Pai;
import br.com.todeschini.itemservicepersistence.entities.enums.TipoFilhoEnum;
import br.com.todeschini.itemservicepersistence.entities.enums.TipoPinturaEnum;
import br.com.todeschini.itemservicepersistence.publico.acessoriousado.AcessorioUsadoDomainToEntityAdapter;
import br.com.todeschini.itemservicepersistence.publico.categoriacomponente.CategoriaComponenteDomainToEntityAdapter;
import br.com.todeschini.itemservicepersistence.publico.cor.CorDomainToEntityAdapter;
import br.com.todeschini.itemservicepersistence.publico.materialusado.MaterialUsadoDomainToEntityAdapter;
import br.com.todeschini.itemservicepersistence.publico.medidas.MedidasDomainToEntityAdapter;
import br.com.todeschini.itemservicepersistence.publico.modelo.ModeloDomainToEntityAdapter;
import br.com.todeschini.libauditdomain.enums.DSituacaoEnum;
import br.com.todeschini.libvalidationhandler.contracts.Convertable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FilhoDomainToEntityAdapter implements Convertable<Filho, DFilho> {

    private final CorDomainToEntityAdapter corDomainToEntityAdapter;
    private final MedidasDomainToEntityAdapter medidasDomainToEntityAdapter;
    //private final RoteiroDomainToEntityAdapter roteiroDomainToEntityAdapter;
    private final MaterialUsadoDomainToEntityAdapter materialUsadoDomainToEntityAdapter;
    private final AcessorioUsadoDomainToEntityAdapter acessorioUsadoDomainToEntityAdapter;
    private final CategoriaComponenteDomainToEntityAdapter categoriaComponenteDomainToEntityAdapter;
    private final ModeloDomainToEntityAdapter modeloDomainToEntityAdapter;

    @Override
    public Filho toEntity(DFilho domain) {

        Set<Filho> filhos = Optional.ofNullable(domain.getFilhos())
                .map(lista -> lista.stream()
                        .map(this::toEntity)
                        .collect(Collectors.toSet())
                )
                .orElse(new HashSet<>());

        List<MaterialUsado> materiaisUsados = Optional.ofNullable(domain.getMateriaisUsados())
                .map(lista -> lista.stream()
                        .map(materialUsadoDomainToEntityAdapter::toEntity)
                        .collect(Collectors.toList())
                )
                .orElse(new ArrayList<>());

        List<AcessorioUsado> acessoriosUsados = Optional.ofNullable(domain.getAcessoriosUsados())
                .map(lista -> lista.stream()
                        .map(acessorioUsadoDomainToEntityAdapter::toEntity)
                        .collect(Collectors.toList())
                )
                .orElse(new ArrayList<>());

        return Filho.builder()
                .cdfilho(domain.getCodigo())
                .descricao(domain.getDescricao())
                .pai(Optional.ofNullable(domain.getPai())
                        .map(pai -> new Pai( // criar objeto na mão para ter acesso aos atributos no criar estrutura. não injetar o adaptador (geraria stackoverflow)
                                pai.getCodigo(),
                                Optional.ofNullable(pai.getModelo())
                                        .map(modeloDomainToEntityAdapter::toEntity)
                                        .orElse(null),
                                Optional.ofNullable(pai.getCategoriaComponente())
                                        .map(categoriaComponenteDomainToEntityAdapter::toEntity)
                                        .orElse(null),
                                pai.getDescricao(),

                                pai.getBordasComprimento(),
                                pai.getBordasLargura(),

                                pai.getNumeroCantoneiras(),
                                pai.getTntUmaFace(),
                                pai.getPlasticoAcima(),
                                pai.getPlasticoAdicional(),
                                pai.getLarguraPlastico(),

                                pai.getFaces(),
                                pai.getEspecial(),
                                Optional.ofNullable(pai.getTipoPintura())
                                        .map(tipoPintura -> TipoPinturaEnum.valueOf(tipoPintura.name()))
                                        .orElse(null),
                            null
                                ))
                        .orElse(null))
                .cor(Optional.ofNullable(domain.getCor())
                        .map(cor -> corDomainToEntityAdapter.toEntity(domain.getCor()))
                        .orElse(null))
                .medidas(Optional.ofNullable(domain.getMedidas())
                        .map(medidas -> medidasDomainToEntityAdapter.toEntity(domain.getMedidas()))
                        .orElse(null))
                /*.roteiro(Optional.ofNullable(domain.getRoteiro())
                        .map(roteiro -> roteiroDomainToEntityAdapter.toEntity(domain.getRoteiro()))
                        .orElse(null))*/
                .roteiroId(Optional.ofNullable(domain.getRoteiro()).map(DRoteiro::getCodigo).orElse(null))
                .unidadeMedida(Optional.ofNullable(domain.getUnidadeMedida()).orElse("UN"))
                .implantacao(domain.getImplantacao())
                .valor(domain.calcularValor())
                .tipo(TipoFilhoEnum.valueOf(domain.getTipo().name()))

                .filhos(filhos)
                .materiaisUsados(materiaisUsados)
                .acessoriosUsados(acessoriosUsados)

                .build();
    }

    @Override
    public DFilho toDomain(Filho entity) {

        List<DFilho> filhos = Optional.ofNullable(entity.getFilhos())
                .map(lista -> lista.stream()
                        .map(this::toDomain)
                        .collect(Collectors.toList())
                )
                .orElse(new ArrayList<>());

        List<DMaterialUsado> materiaisUsados = Optional.ofNullable(entity.getMateriaisUsados())
                .map(lista -> lista.stream()
                        .map(materialUsadoDomainToEntityAdapter::toDomain)
                        .collect(Collectors.toList())
                )
                .orElse(new ArrayList<>());

        List<DAcessorioUsado> acessoriosUsados = Optional.ofNullable(entity.getAcessoriosUsados())
                .map(lista -> lista.stream()
                        .map(acessorioUsadoDomainToEntityAdapter::toDomain)
                        .collect(Collectors.toList())
                )
                .orElse(new ArrayList<>());

        return DFilho.builder()
                .codigo(entity.getCdfilho())
                .descricao(entity.getDescricao())
                .pai(Optional.ofNullable(entity.getPai())
                        .map(pai -> new DPai(
                                pai.getCdpai(),
                                Optional.ofNullable(pai.getModelo())
                                        .map(modeloDomainToEntityAdapter::toDomain)
                                        .orElse(null),
                                Optional.ofNullable(pai.getCategoriaComponente())
                                        .map(categoriaComponenteDomainToEntityAdapter::toDomain)
                                        .orElse(null),
                                pai.getDescricao(),

                                pai.getBordasComprimento(),
                                pai.getBordasLargura(),

                                pai.getNumeroCantoneiras(),
                                pai.getTntUmaFace(),
                                pai.getPlasticoAcima(),
                                pai.getPlasticoAdicional(),
                                pai.getLarguraPlastico(),

                                pai.getFaces(),
                                pai.getEspecial(),
                                Optional.ofNullable(pai.getTipoPintura())
                                        .map(tipoPintura -> DTipoPinturaEnum.valueOf(tipoPintura.name()))
                                        .orElse(null),

                                DSituacaoEnum.valueOf(pai.getSituacao().name()),
                                null
                        ))
                        .orElse(null))
                .cor(Optional.ofNullable(entity.getCor())
                        .map(cor -> corDomainToEntityAdapter.toDomain(entity.getCor()))
                        .orElse(null))
                .medidas(Optional.ofNullable(entity.getMedidas())
                        .map(medidas -> medidasDomainToEntityAdapter.toDomain(entity.getMedidas()))
                        .orElse(null))
                /*.roteiro(Optional.ofNullable(entity.getRoteiro())
                        .map(roteiro -> roteiroDomainToEntityAdapter.toDomain(entity.getRoteiro()))
                        .orElse(null))*/
                .roteiro(Optional.ofNullable(entity.getRoteiroId()).map(DRoteiro::new).orElse(null))
                .unidadeMedida(Optional.ofNullable(entity.getUnidadeMedida()).orElse("UN"))
                .implantacao(entity.getImplantacao())
                .valor(entity.getValor())
                .tipo(Optional.ofNullable(entity.getTipo())
                        .map(tipoFilho -> DTipoFilhoEnum.valueOf(tipoFilho.name()))
                        .orElse(null))
                .situacao(Optional.ofNullable(entity.getSituacao())
                        .map(situacao -> DSituacaoEnum.valueOf(situacao.name()))
                        .orElse(null))

                .filhos(filhos)
                .materiaisUsados(materiaisUsados)
                .acessoriosUsados(acessoriosUsados)

                .build();
    }
}
