package br.com.todeschini.persistence.mdf.paintingborderbackground;

import br.com.todeschini.domain.business.enums.DStatus;
import br.com.todeschini.domain.business.mdf.paintingborderbackground.DPaintingBorderBackground;
import br.com.todeschini.persistence.entities.mdf.PaintingBorderBackground;
import br.com.todeschini.persistence.util.Convertable;
import org.springframework.stereotype.Component;

@Component
public class PaintingBorderBackgroundDomainToEntityAdapter implements Convertable<PaintingBorderBackground, DPaintingBorderBackground> {

    @Override
    public PaintingBorderBackground toEntity(DPaintingBorderBackground domain) {
        PaintingBorderBackground entity = new PaintingBorderBackground();
        entity.setCode(domain.getCode());
        entity.setDescription(domain.getDescription().toUpperCase());
        entity.setFamily(domain.getFamily());
        entity.setImplementation(domain.getImplementation());
        entity.setLostPercentage(domain.getLostPercentage());
        entity.setValue(domain.getValue());
        return entity;
    }

    @Override
    public DPaintingBorderBackground toDomain(PaintingBorderBackground entity) {
        DPaintingBorderBackground domain = new DPaintingBorderBackground();
        domain.setCode(entity.getCode());
        domain.setDescription(entity.getDescription().toUpperCase());
        domain.setFamily(entity.getFamily());
        domain.setImplementation(entity.getImplementation());
        domain.setLostPercentage(entity.getLostPercentage());
        domain.setValue(entity.getValue());
        domain.setStatus(DStatus.valueOf(entity.getStatus().name()));
        return domain;
    }
}
