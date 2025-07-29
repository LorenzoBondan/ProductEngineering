package br.com.todeschini.mdpservicepersistence.publico.chapa;

import br.com.todeschini.itemservicedomain.enums.DTipoMaterialEnum;
import br.com.todeschini.itemservicepersistence.entities.Cor;
import br.com.todeschini.itemservicepersistence.entities.enums.TipoMaterialEnum;
import br.com.todeschini.itemservicepersistence.publico.cor.CorDomainToEntityAdapter;
import br.com.todeschini.libauditdomain.enums.DSituacaoEnum;
import br.com.todeschini.libvalidationhandler.contracts.Convertable;
import br.com.todeschini.mdpservicedomain.chapa.DChapa;
import br.com.todeschini.mdpservicepersistence.entities.Chapa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ChapaDomainToEntityAdapter implements Convertable<Chapa, DChapa> {

    private final CorDomainToEntityAdapter corDomainToEntityAdapter;

    @Override
    public Chapa toEntity(DChapa domain) {
        Chapa chapa = new Chapa();
        chapa.setCdmaterial(domain.getCodigo());
        chapa.setDescricao(domain.getDescricao());
        chapa.setTipoMaterial(TipoMaterialEnum.valueOf(domain.getTipoMaterial().name()));
        chapa.setImplantacao(domain.getImplantacao());
        chapa.setPorcentagemPerda(domain.getPorcentagemPerda());
        chapa.setValor(domain.getValor());
        chapa.setCor(Optional.ofNullable(domain.getCor())
                .map(cor -> new Cor(cor.getCodigo()))
                .orElse(null));

        chapa.setEspessura(domain.getEspessura());
        chapa.setFaces(domain.getFaces());
        return chapa;
    }

    @Override
    public DChapa toDomain(Chapa entity) {
        DChapa chapa = new DChapa();
        chapa.setCodigo(entity.getCdmaterial());
        chapa.setDescricao(entity.getDescricao());
        chapa.setTipoMaterial(Optional.ofNullable(entity.getTipoMaterial())
                .map(tipoMaterial -> DTipoMaterialEnum.valueOf(entity.getTipoMaterial().name()))
                .orElse(null));
        chapa.setImplantacao(entity.getImplantacao());
        chapa.setPorcentagemPerda(entity.getPorcentagemPerda());
        chapa.setValor(entity.getValor());
        chapa.setCor(Optional.ofNullable(entity.getCor())
                .map(corDomainToEntityAdapter::toDomain)
                .orElse(null));
        chapa.setEspessura(entity.getEspessura());
        chapa.setFaces(entity.getFaces());
        chapa.setSituacao(Optional.ofNullable(entity.getSituacao())
                .map(situacao -> DSituacaoEnum.valueOf(situacao.name()))
                .orElse(null));
        return chapa;
    }
}
