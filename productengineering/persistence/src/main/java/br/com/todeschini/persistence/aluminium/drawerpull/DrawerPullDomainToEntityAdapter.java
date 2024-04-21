package br.com.todeschini.persistence.aluminium.drawerpull;

import br.com.todeschini.domain.business.aluminium.drawerpull.DDrawerPull;
import br.com.todeschini.persistence.entities.aluminium.DrawerPull;
import br.com.todeschini.persistence.util.Convertable;
import org.springframework.stereotype.Component;

@Component
public class DrawerPullDomainToEntityAdapter implements Convertable<DrawerPull, DDrawerPull> {

    @Override
    public DrawerPull toEntity(DDrawerPull domain) {
        DrawerPull entity = new DrawerPull();
        entity.setCode(domain.getCode());
        entity.setDescription(domain.getDescription());
        entity.setMeasure1(domain.getMeasure1());
        entity.setMeasure2(domain.getMeasure2());
        entity.setMeasure3(domain.getMeasure3());
        entity.setImplementation(domain.getImplementation());
        return entity;
    }

    @Override
    public DDrawerPull toDomain(DrawerPull entity) {
        DDrawerPull domain = new DDrawerPull();
        domain.setCode(entity.getCode());
        domain.setDescription(entity.getDescription());
        domain.setMeasure1(entity.getMeasure1());
        domain.setMeasure2(entity.getMeasure2());
        domain.setMeasure3(entity.getMeasure3());
        domain.setMeasurementUnit(entity.getMeasurementUnit());
        domain.setImplementation(entity.getImplementation());
        return domain;
    }
}
