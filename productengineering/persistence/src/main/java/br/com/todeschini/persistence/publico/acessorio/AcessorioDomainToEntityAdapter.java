package br.com.todeschini.persistence.publico.acessorio;

import br.com.todeschini.domain.Convertable;
import br.com.todeschini.domain.business.enums.DSituacaoEnum;
import br.com.todeschini.domain.business.publico.acessorio.DAcessorio;
import br.com.todeschini.domain.metadata.EntityAdapter;
import br.com.todeschini.persistence.entities.publico.Acessorio;
import br.com.todeschini.persistence.entities.publico.Cor;
import br.com.todeschini.persistence.entities.publico.Medidas;
import br.com.todeschini.persistence.publico.cor.CorDomainToEntityAdapter;
import br.com.todeschini.persistence.publico.medidas.MedidasDomainToEntityAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@EntityAdapter(entityClass = Acessorio.class)
public class AcessorioDomainToEntityAdapter implements Convertable<Acessorio, DAcessorio> {

    @Autowired
    private CorDomainToEntityAdapter corDomainToEntityAdapter;
    @Autowired
    private MedidasDomainToEntityAdapter medidasDomainToEntityAdapter;

    @Override
    public Acessorio toEntity(DAcessorio domain) {
        Acessorio acessorio = new Acessorio();
        acessorio.setCdacessorio(domain.getCodigo());
        acessorio.setDescricao(domain.getDescricao());
        acessorio.setMedidas(Optional.ofNullable(domain.getMedidas())
                .map(medidas -> new Medidas(medidas.getCodigo()))
                .orElse(null));
        acessorio.setCor(Optional.ofNullable(domain.getCor())
                .map(cor -> new Cor(cor.getCodigo()))
                .orElse(null));
        acessorio.setImplantacao(domain.getImplantacao());
        acessorio.setValor(domain.getValor());
        return acessorio;
    }

    @Override
    public DAcessorio toDomain(Acessorio entity) {
        DAcessorio acessorio = new DAcessorio();
        acessorio.setCodigo(entity.getCdacessorio());
        acessorio.setDescricao(entity.getDescricao());
        acessorio.setMedidas(Optional.ofNullable(entity.getMedidas())
                .map(medidas -> medidasDomainToEntityAdapter.toDomain(medidas))
                .orElse(null));
        acessorio.setCor(Optional.ofNullable(entity.getCor())
                .map(cor -> corDomainToEntityAdapter.toDomain(cor))
                .orElse(null));
        acessorio.setImplantacao(entity.getImplantacao());
        acessorio.setValor(entity.getValor());
        acessorio.setSituacao(DSituacaoEnum.valueOf(entity.getSituacao().name()));
        return acessorio;
    }
}
