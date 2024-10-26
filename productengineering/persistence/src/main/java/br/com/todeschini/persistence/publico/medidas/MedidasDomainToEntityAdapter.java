package br.com.todeschini.persistence.publico.medidas;

import br.com.todeschini.domain.Convertable;
import br.com.todeschini.domain.business.enums.DSituacao;
import br.com.todeschini.domain.business.publico.medidas.DMedidas;
import br.com.todeschini.domain.metadata.EntityAdapter;
import br.com.todeschini.persistence.entities.publico.Medidas;
import org.springframework.stereotype.Component;

@Component
@EntityAdapter(entityClass = Medidas.class)
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
                .situacao(DSituacao.valueOf(entity.getSituacao().name()))
                .build();
    }
}
