package br.com.todeschini.persistence.aluminium.glass;

import br.com.todeschini.domain.business.aluminium.glass.DGlass;
import br.com.todeschini.persistence.entities.aluminium.Glass;
import br.com.todeschini.persistence.publico.color.ColorDomainToEntityAdapter;
import br.com.todeschini.persistence.publico.color.ColorRepository;
import br.com.todeschini.persistence.util.Convertable;
import org.springframework.stereotype.Component;

@Component
public class GlassDomainToEntityAdapter implements Convertable<Glass, DGlass> {

    private final ColorRepository colorRepository;
    private final ColorDomainToEntityAdapter colorAdapter;

    public GlassDomainToEntityAdapter(ColorRepository colorRepository, ColorDomainToEntityAdapter colorAdapter) {
        this.colorRepository = colorRepository;
        this.colorAdapter = colorAdapter;
    }

    @Override
    public Glass toEntity(DGlass domain) {
        Glass entity = new Glass();
        entity.setCode(domain.getCode());
        entity.setDescription(domain.getDescription());
        entity.setMeasure1(domain.getMeasure1());
        entity.setMeasure2(domain.getMeasure2());
        entity.setMeasure3(domain.getMeasure3());
        entity.setImplementation(domain.getImplementation());
        entity.setColor(colorRepository.findById(domain.getColor().getCode()).get());
        entity.setValue(domain.getValue());
        return entity;
    }

    @Override
    public DGlass toDomain(Glass entity) {
        DGlass domain = new DGlass();
        domain.setCode(entity.getCode());
        domain.setDescription(entity.getDescription());
        domain.setMeasure1(entity.getMeasure1());
        domain.setMeasure2(entity.getMeasure2());
        domain.setMeasure3(entity.getMeasure3());
        domain.setMeasurementUnit(entity.getMeasurementUnit());
        domain.setImplementation(entity.getImplementation());
        domain.setColor(colorAdapter.toDomain(entity.getColor()));
        domain.setValue(entity.getValue());
        return domain;
    }
}
