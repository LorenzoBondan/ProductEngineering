package br.com.todeschini.persistence.mdf.polyester;

import br.com.todeschini.domain.business.mdf.polyester.DPolyester;
import br.com.todeschini.persistence.entities.mdf.Polyester;
import br.com.todeschini.persistence.util.Convertable;
import org.springframework.stereotype.Component;

@Component
public class PolyesterDomainToEntityAdapter implements Convertable<Polyester, DPolyester> {

    @Override
    public Polyester toEntity(DPolyester domain) {
        Polyester entity = new Polyester();
        entity.setCode(domain.getCode());
        entity.setDescription(domain.getDescription().toUpperCase());
        entity.setFamily(domain.getFamily());
        entity.setImplementation(domain.getImplementation());
        entity.setLostPercentage(domain.getLostPercentage());
        entity.setValue(domain.getValue());
        return entity;
    }

    @Override
    public DPolyester toDomain(Polyester entity) {
        DPolyester domain = new DPolyester();
        domain.setCode(entity.getCode());
        domain.setDescription(entity.getDescription().toUpperCase());
        domain.setFamily(entity.getFamily());
        domain.setImplementation(entity.getImplementation());
        domain.setLostPercentage(entity.getLostPercentage());
        domain.setValue(entity.getValue());
        return domain;
    }
}
