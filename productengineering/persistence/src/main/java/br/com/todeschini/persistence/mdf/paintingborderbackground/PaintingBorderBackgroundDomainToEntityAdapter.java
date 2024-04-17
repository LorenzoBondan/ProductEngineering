package br.com.todeschini.persistence.mdf.paintingborderbackground;

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
        entity.setDescription(domain.getDescription());
        entity.setFamily(domain.getFamily());
        entity.setImplementation(domain.getImplementation());
        entity.setLostPercentage(domain.getLostPercentage());
        return entity;
    }

    @Override
    public DPaintingBorderBackground toDomain(PaintingBorderBackground entity) {
        DPaintingBorderBackground domain = new DPaintingBorderBackground();
        domain.setCode(entity.getCode());
        domain.setDescription(entity.getDescription());
        domain.setFamily(entity.getFamily());
        domain.setImplementation(entity.getImplementation());
        domain.setLostPercentage(entity.getLostPercentage());
        return domain;
    }
}
