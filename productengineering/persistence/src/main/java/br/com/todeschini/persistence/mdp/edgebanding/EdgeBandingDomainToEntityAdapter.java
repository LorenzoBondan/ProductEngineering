package br.com.todeschini.persistence.mdp.edgebanding;

import br.com.todeschini.domain.business.mdp.edgebanding.DEdgeBanding;
import br.com.todeschini.persistence.entities.mdp.EdgeBanding;
import br.com.todeschini.persistence.util.Convertable;
import org.springframework.stereotype.Component;

@Component
public class EdgeBandingDomainToEntityAdapter implements Convertable<EdgeBanding, DEdgeBanding> {

    @Override
    public EdgeBanding toEntity(DEdgeBanding domain) {
        EdgeBanding entity = new EdgeBanding();
        entity.setCode(domain.getCode());
        entity.setDescription(domain.getDescription());
        entity.setFamily(domain.getFamily());
        entity.setImplementation(domain.getImplementation());
        entity.setLostPercentage(domain.getLostPercentage());
        entity.setHeight(domain.getHeight());
        entity.setThickness(domain.getThickness());
        return entity;
    }

    @Override
    public DEdgeBanding toDomain(EdgeBanding entity) {
        DEdgeBanding domain = new DEdgeBanding();
        domain.setCode(entity.getCode());
        domain.setDescription(entity.getDescription());
        domain.setFamily(entity.getFamily());
        domain.setImplementation(entity.getImplementation());
        domain.setLostPercentage(entity.getLostPercentage());
        domain.setHeight(entity.getHeight());
        domain.setThickness(entity.getThickness());
        return domain;
    }
}
