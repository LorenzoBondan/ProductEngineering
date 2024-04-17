package br.com.todeschini.persistence.mdf.painting;

import br.com.todeschini.domain.business.mdf.painting.DPainting;
import br.com.todeschini.persistence.entities.mdf.Painting;
import br.com.todeschini.persistence.mdf.paintingtype.PaintingTypeDomainToEntityAdapter;
import br.com.todeschini.persistence.mdf.paintingtype.PaintingTypeRepository;
import br.com.todeschini.persistence.util.Convertable;
import org.springframework.stereotype.Component;

@Component
public class PaintingDomainToEntityAdapter implements Convertable<Painting, DPainting> {

    private final PaintingTypeRepository paintingTypeRepository;
    private final PaintingTypeDomainToEntityAdapter paintingTypeAdapter;

    public PaintingDomainToEntityAdapter(PaintingTypeRepository paintingTypeRepository, PaintingTypeDomainToEntityAdapter paintingTypeAdapter) {
        this.paintingTypeRepository = paintingTypeRepository;
        this.paintingTypeAdapter = paintingTypeAdapter;
    }

    @Override
    public Painting toEntity(DPainting domain) {
        Painting entity = new Painting();
        entity.setCode(domain.getCode());
        entity.setDescription(domain.getDescription());
        entity.setFamily(domain.getFamily());
        entity.setImplementation(domain.getImplementation());
        entity.setLostPercentage(domain.getLostPercentage());
        entity.setPaintingType(paintingTypeRepository.findById(domain.getPaintingType().getId()).get());
        return entity;
    }

    @Override
    public DPainting toDomain(Painting entity) {
        DPainting domain = new DPainting();
        domain.setCode(entity.getCode());
        domain.setDescription(entity.getDescription());
        domain.setFamily(entity.getFamily());
        domain.setImplementation(entity.getImplementation());
        domain.setLostPercentage(entity.getLostPercentage());
        domain.setPaintingType(paintingTypeAdapter.toDomain(entity.getPaintingType()));
        return domain;
    }
}
