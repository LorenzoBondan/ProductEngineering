package br.com.todeschini.persistence.aluminium.molding;

import br.com.todeschini.domain.business.aluminium.molding.DMolding;
import br.com.todeschini.persistence.entities.aluminium.Molding;
import br.com.todeschini.persistence.util.Convertable;
import org.springframework.stereotype.Component;

@Component
public class MoldingDomainToEntityAdapter implements Convertable<Molding, DMolding> {

    @Override
    public Molding toEntity(DMolding domain) {
        Molding entity = new Molding();
        entity.setCode(domain.getCode());
        entity.setDescription(domain.getDescription());
        entity.setMeasure1(domain.getMeasure1());
        entity.setMeasure2(domain.getMeasure2());
        entity.setMeasure3(domain.getMeasure3());
        return entity;
    }

    @Override
    public DMolding toDomain(Molding entity) {
        DMolding domain = new DMolding();
        domain.setCode(entity.getCode());
        domain.setDescription(entity.getDescription());
        domain.setMeasure1(entity.getMeasure1());
        domain.setMeasure2(entity.getMeasure2());
        domain.setMeasure3(entity.getMeasure3());
        domain.setMeasurementUnit(entity.getMeasurementUnit());
        return domain;
    }
}
