package br.com.todeschini.persistence.mdp.edgebanding;

import br.com.todeschini.domain.business.mdp.edgebanding.DEdgeBanding;
import br.com.todeschini.persistence.entities.mdp.EdgeBanding;
import br.com.todeschini.persistence.publico.color.ColorDomainToEntityAdapter;
import br.com.todeschini.persistence.publico.color.ColorRepository;
import br.com.todeschini.persistence.util.Convertable;
import org.springframework.stereotype.Component;

@Component
public class EdgeBandingDomainToEntityAdapter implements Convertable<EdgeBanding, DEdgeBanding> {

    private final ColorRepository colorRepository;
    private final ColorDomainToEntityAdapter colorAdapter;

    public EdgeBandingDomainToEntityAdapter(ColorRepository colorRepository, ColorDomainToEntityAdapter colorAdapter) {
        this.colorRepository = colorRepository;
        this.colorAdapter = colorAdapter;
    }

    @Override
    public EdgeBanding toEntity(DEdgeBanding domain) {
        EdgeBanding entity = new EdgeBanding();
        entity.setCode(domain.getCode());
        entity.setDescription(domain.getDescription());
        entity.setFamily(domain.getFamily());
        entity.setImplementation(domain.getImplementation());
        entity.setLostPercentage(domain.getLostPercentage());
        entity.setColor(colorRepository.findById(domain.getColor().getCode()).get());
        entity.setHeight(domain.getHeight());
        entity.setThickness(domain.getThickness());
        entity.setValue(domain.getValue());
        return entity;
    }

    @Override
    public DEdgeBanding toDomain(EdgeBanding entity) {
        DEdgeBanding domain = new DEdgeBanding();
        domain.setCode(entity.getCode());
        domain.setDescription(entity.getDescription());
        domain.setFamily(entity.getFamily());
        domain.setImplementation(entity.getImplementation());
        domain.setColor(colorAdapter.toDomain(entity.getColor()));
        domain.setLostPercentage(entity.getLostPercentage());
        domain.setHeight(entity.getHeight());
        domain.setThickness(entity.getThickness());
        domain.setValue(entity.getValue());
        return domain;
    }
}
