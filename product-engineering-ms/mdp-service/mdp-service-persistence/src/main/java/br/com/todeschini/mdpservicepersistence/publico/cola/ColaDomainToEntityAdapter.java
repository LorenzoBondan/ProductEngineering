package br.com.todeschini.mdpservicepersistence.publico.cola;

import br.com.todeschini.itemservicedomain.enums.DTipoMaterialEnum;
import br.com.todeschini.itemservicepersistence.entities.Cor;
import br.com.todeschini.itemservicepersistence.entities.enums.TipoMaterialEnum;
import br.com.todeschini.itemservicepersistence.publico.cor.CorDomainToEntityAdapter;
import br.com.todeschini.libauditdomain.enums.DSituacaoEnum;
import br.com.todeschini.libvalidationhandler.contracts.Convertable;
import br.com.todeschini.mdpservicedomain.cola.DCola;
import br.com.todeschini.mdpservicepersistence.entities.Cola;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ColaDomainToEntityAdapter implements Convertable<Cola, DCola> {

    private final CorDomainToEntityAdapter corDomainToEntityAdapter;

    @Override
    public Cola toEntity(DCola domain) {
        Cola cola = new Cola();
        cola.setCdmaterial(domain.getCodigo());
        cola.setDescricao(domain.getDescricao());
        cola.setTipoMaterial(TipoMaterialEnum.valueOf(domain.getTipoMaterial().name()));
        cola.setImplantacao(domain.getImplantacao());
        cola.setPorcentagemPerda(domain.getPorcentagemPerda());
        cola.setValor(domain.getValor());
        cola.setCor(Optional.ofNullable(domain.getCor())
                .map(cor -> new Cor(cor.getCodigo()))
                .orElse(null));

        cola.setGramatura(domain.getGramatura());
        return cola;
    }

    @Override
    public DCola toDomain(Cola entity) {
        DCola cola = new DCola();
        cola.setCodigo(entity.getCdmaterial());
        cola.setDescricao(entity.getDescricao());
        cola.setTipoMaterial(Optional.ofNullable(entity.getTipoMaterial())
                .map(tipoMaterial -> DTipoMaterialEnum.valueOf(entity.getTipoMaterial().name()))
                .orElse(null));
        cola.setImplantacao(entity.getImplantacao());
        cola.setPorcentagemPerda(entity.getPorcentagemPerda());
        cola.setValor(entity.getValor());
        cola.setCor(Optional.ofNullable(entity.getCor())
                .map(corDomainToEntityAdapter::toDomain)
                .orElse(null));
        cola.setGramatura(entity.getGramatura());
        cola.setSituacao(Optional.ofNullable(entity.getSituacao())
                .map(situacao -> DSituacaoEnum.valueOf(situacao.name()))
                .orElse(null));
        return cola;
    }
}
