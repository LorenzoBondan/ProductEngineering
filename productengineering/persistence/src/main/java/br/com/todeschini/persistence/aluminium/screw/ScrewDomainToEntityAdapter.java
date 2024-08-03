package br.com.todeschini.persistence.aluminium.screw;

import br.com.todeschini.domain.business.aluminium.screw.DScrew;
import br.com.todeschini.domain.business.enums.DStatus;
import br.com.todeschini.persistence.entities.aluminium.Screw;
import br.com.todeschini.persistence.util.Convertable;
import org.springframework.stereotype.Component;

@Component
public class ScrewDomainToEntityAdapter implements Convertable<Screw, DScrew> {

    @Override
    public Screw toEntity(DScrew domain) {
        Screw entity = new Screw();
        entity.setCode(domain.getCode());
        entity.setDescription(domain.getDescription().toUpperCase());
        entity.setMeasure1(domain.getMeasure1());
        entity.setMeasure2(domain.getMeasure2());
        entity.setMeasure3(domain.getMeasure3());
        entity.setImplementation(domain.getImplementation());
        entity.setValue(domain.getValue());
        return entity;
    }

    @Override
    public DScrew toDomain(Screw entity) {
        DScrew domain = new DScrew();
        domain.setCode(entity.getCode());
        domain.setDescription(entity.getDescription().toUpperCase());
        domain.setMeasure1(entity.getMeasure1());
        domain.setMeasure2(entity.getMeasure2());
        domain.setMeasure3(entity.getMeasure3());
        domain.setMeasurementUnit(entity.getMeasurementUnit());
        domain.setImplementation(entity.getImplementation());
        domain.setValue(entity.getValue());
        domain.setStatus(DStatus.valueOf(entity.getStatus().name()));
        return domain;
    }
}
