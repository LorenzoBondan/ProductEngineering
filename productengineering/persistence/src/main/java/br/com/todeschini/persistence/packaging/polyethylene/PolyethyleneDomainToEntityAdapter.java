package br.com.todeschini.persistence.packaging.polyethylene;

import br.com.todeschini.domain.business.packaging.polythylene.DPolyethylene;
import br.com.todeschini.persistence.entities.packaging.Polyethylene;
import br.com.todeschini.persistence.util.Convertable;
import org.springframework.stereotype.Component;

@Component
public class PolyethyleneDomainToEntityAdapter implements Convertable<Polyethylene, DPolyethylene> {

    @Override
    public Polyethylene toEntity(DPolyethylene domain) {
        Polyethylene entity = new Polyethylene();
        entity.setCode(domain.getCode());
        entity.setDescription(domain.getDescription());
        entity.setFamily(domain.getFamily());
        entity.setImplementation(domain.getImplementation());
        entity.setLostPercentage(domain.getLostPercentage());
        return entity;
    }

    @Override
    public DPolyethylene toDomain(Polyethylene entity) {
        DPolyethylene domain = new DPolyethylene();
        domain.setCode(entity.getCode());
        domain.setDescription(entity.getDescription());
        domain.setFamily(entity.getFamily());
        domain.setImplementation(entity.getImplementation());
        domain.setLostPercentage(entity.getLostPercentage());
        return domain;
    }
}
