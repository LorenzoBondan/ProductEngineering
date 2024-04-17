package br.com.todeschini.persistence.packaging.plastic;

import br.com.todeschini.domain.business.packaging.plastic.DPlastic;
import br.com.todeschini.persistence.entities.packaging.Plastic;
import br.com.todeschini.persistence.util.Convertable;
import org.springframework.stereotype.Component;

@Component
public class PlasticDomainToEntityAdapter implements Convertable<Plastic, DPlastic> {

    @Override
    public Plastic toEntity(DPlastic domain) {
        Plastic entity = new Plastic();
        entity.setCode(domain.getCode());
        entity.setDescription(domain.getDescription());
        entity.setFamily(domain.getFamily());
        entity.setImplementation(domain.getImplementation());
        entity.setLostPercentage(domain.getLostPercentage());
        entity.setGrammage(domain.getGrammage());
        return entity;
    }

    @Override
    public DPlastic toDomain(Plastic entity) {
        DPlastic domain = new DPlastic();
        domain.setCode(entity.getCode());
        domain.setDescription(entity.getDescription());
        domain.setFamily(entity.getFamily());
        domain.setImplementation(entity.getImplementation());
        domain.setLostPercentage(entity.getLostPercentage());
        domain.setGrammage(entity.getGrammage());
        return domain;
    }
}
