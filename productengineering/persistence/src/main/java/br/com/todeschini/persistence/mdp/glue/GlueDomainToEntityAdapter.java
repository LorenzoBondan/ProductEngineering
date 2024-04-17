package br.com.todeschini.persistence.mdp.glue;

import br.com.todeschini.domain.business.mdp.glue.DGlue;
import br.com.todeschini.persistence.entities.mdp.Glue;
import br.com.todeschini.persistence.util.Convertable;
import org.springframework.stereotype.Component;

@Component
public class GlueDomainToEntityAdapter implements Convertable<Glue, DGlue> {

    @Override
    public Glue toEntity(DGlue domain) {
        Glue entity = new Glue();
        entity.setCode(domain.getCode());
        entity.setDescription(domain.getDescription());
        entity.setFamily(domain.getFamily());
        entity.setImplementation(domain.getImplementation());
        entity.setLostPercentage(domain.getLostPercentage());
        entity.setGrammage(domain.getGrammage());
        return entity;
    }

    @Override
    public DGlue toDomain(Glue entity) {
        DGlue domain = new DGlue();
        domain.setCode(entity.getCode());
        domain.setDescription(entity.getDescription());
        domain.setFamily(entity.getFamily());
        domain.setImplementation(entity.getImplementation());
        domain.setLostPercentage(entity.getLostPercentage());
        domain.setGrammage(entity.getGrammage());
        return domain;
    }
}
