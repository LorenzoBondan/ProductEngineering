package br.com.todeschini.persistence.aluminium.trysquare;

import br.com.todeschini.domain.business.aluminium.trysquare.DTrySquare;
import br.com.todeschini.persistence.entities.aluminium.TrySquare;
import br.com.todeschini.persistence.util.Convertable;
import org.springframework.stereotype.Component;

@Component
public class TrySquareDomainToEntityAdapter implements Convertable<TrySquare, DTrySquare> {

    @Override
    public TrySquare toEntity(DTrySquare domain) {
        TrySquare entity = new TrySquare();
        entity.setCode(domain.getCode());
        entity.setDescription(domain.getDescription());
        entity.setMeasure1(domain.getMeasure1());
        entity.setMeasure2(domain.getMeasure2());
        entity.setMeasure3(domain.getMeasure3());
        return entity;
    }

    @Override
    public DTrySquare toDomain(TrySquare entity) {
        DTrySquare domain = new DTrySquare();
        domain.setCode(entity.getCode());
        domain.setDescription(entity.getDescription());
        domain.setMeasure1(entity.getMeasure1());
        domain.setMeasure2(entity.getMeasure2());
        domain.setMeasure3(entity.getMeasure3());
        domain.setMeasurementUnit(entity.getMeasurementUnit());
        return domain;
    }
}
