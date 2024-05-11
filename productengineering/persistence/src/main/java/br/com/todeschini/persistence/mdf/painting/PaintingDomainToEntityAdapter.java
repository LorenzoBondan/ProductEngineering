package br.com.todeschini.persistence.mdf.painting;

import br.com.todeschini.domain.business.mdf.painting.DPainting;
import br.com.todeschini.persistence.entities.mdf.Painting;
import br.com.todeschini.persistence.mdf.paintingtype.PaintingTypeDomainToEntityAdapter;
import br.com.todeschini.persistence.mdf.paintingtype.PaintingTypeRepository;
import br.com.todeschini.persistence.publico.color.ColorDomainToEntityAdapter;
import br.com.todeschini.persistence.publico.color.ColorRepository;
import br.com.todeschini.persistence.util.Convertable;
import org.springframework.stereotype.Component;

@Component
public class PaintingDomainToEntityAdapter implements Convertable<Painting, DPainting> {

    private final PaintingTypeRepository paintingTypeRepository;
    private final PaintingTypeDomainToEntityAdapter paintingTypeAdapter;
    private final ColorRepository colorRepository;
    private final ColorDomainToEntityAdapter colorAdapter;

    public PaintingDomainToEntityAdapter(PaintingTypeRepository paintingTypeRepository, PaintingTypeDomainToEntityAdapter paintingTypeAdapter,
                                         ColorRepository colorRepository, ColorDomainToEntityAdapter colorAdapter) {
        this.paintingTypeRepository = paintingTypeRepository;
        this.paintingTypeAdapter = paintingTypeAdapter;
        this.colorRepository = colorRepository;
        this.colorAdapter = colorAdapter;
    }

    @Override
    public Painting toEntity(DPainting domain) {
        Painting entity = new Painting();
        entity.setCode(domain.getCode());
        entity.setDescription(domain.getDescription().toUpperCase());
        entity.setFamily(domain.getFamily());
        entity.setImplementation(domain.getImplementation());
        entity.setLostPercentage(domain.getLostPercentage());
        if(domain.getColor() != null){
            entity.setColor(colorRepository.findById(domain.getColor().getCode()).get());
        }
        entity.setPaintingType(paintingTypeRepository.findById(domain.getPaintingType().getId()).get());
        entity.setValue(domain.getValue());
        return entity;
    }

    @Override
    public DPainting toDomain(Painting entity) {
        DPainting domain = new DPainting();
        domain.setCode(entity.getCode());
        domain.setDescription(entity.getDescription().toUpperCase());
        domain.setFamily(entity.getFamily());
        domain.setImplementation(entity.getImplementation());
        domain.setLostPercentage(entity.getLostPercentage());
        if(entity.getColor() != null){
            domain.setColor(colorAdapter.toDomain(entity.getColor()));
        }
        domain.setPaintingType(paintingTypeAdapter.toDomain(entity.getPaintingType()));
        domain.setValue(entity.getValue());
        return domain;
    }
}
