package br.com.todeschini.persistence.mdf.paintingtype;

import br.com.todeschini.domain.business.enums.DStatus;
import br.com.todeschini.domain.business.mdf.paintingtype.DPaintingType;
import br.com.todeschini.persistence.entities.mdf.PaintingType;
import br.com.todeschini.persistence.util.Convertable;
import org.springframework.stereotype.Component;

@Component
public class PaintingTypeDomainToEntityAdapter implements Convertable<PaintingType, DPaintingType> {

    @Override
    public PaintingType toEntity(DPaintingType domain) {
        PaintingType entity = new PaintingType();
        entity.setId(domain.getId());
        entity.setDescription(domain.getDescription());
        return entity;
    }

    @Override
    public DPaintingType toDomain(PaintingType entity) {
        DPaintingType domain = new DPaintingType();
        domain.setId(entity.getId());
        domain.setDescription(entity.getDescription());
        domain.setStatus(DStatus.valueOf(entity.getStatus().name()));
        return domain;
    }
}
