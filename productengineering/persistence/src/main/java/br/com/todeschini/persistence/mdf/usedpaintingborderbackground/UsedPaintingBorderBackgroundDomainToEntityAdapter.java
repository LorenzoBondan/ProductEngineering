package br.com.todeschini.persistence.mdf.usedpaintingborderbackground;

import br.com.todeschini.domain.business.mdf.usedpaintingborderbackground.DUsedPaintingBorderBackground;
import br.com.todeschini.domain.exceptions.ResourceNotFoundException;
import br.com.todeschini.persistence.entities.mdf.PaintingSon;
import br.com.todeschini.persistence.entities.mdf.UsedPaintingBorderBackground;
import br.com.todeschini.persistence.mdf.paintingborderbackground.PaintingBorderBackgroundRepository;
import br.com.todeschini.persistence.publico.item.ItemRepository;
import br.com.todeschini.persistence.util.Convertable;
import org.springframework.stereotype.Component;

@Component
public class UsedPaintingBorderBackgroundDomainToEntityAdapter implements Convertable<UsedPaintingBorderBackground, DUsedPaintingBorderBackground> {

    private final PaintingBorderBackgroundRepository paintingBorderBackgroundRepository;
    private final ItemRepository itemRepository;

    public UsedPaintingBorderBackgroundDomainToEntityAdapter(PaintingBorderBackgroundRepository paintingBorderBackgroundRepository, ItemRepository itemRepository){
        this.paintingBorderBackgroundRepository = paintingBorderBackgroundRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public UsedPaintingBorderBackground toEntity(DUsedPaintingBorderBackground domain) {
        UsedPaintingBorderBackground entity = new UsedPaintingBorderBackground();
        entity.setId(domain.getId());
        entity.setPaintingBorderBackground(paintingBorderBackgroundRepository.findById(domain.getPaintingBorderBackgroundCode()).orElseThrow(() -> new ResourceNotFoundException("PaintingBorderBackground não encontrado")));
        if(itemRepository.existsById(domain.getPaintingSonCode())){
            entity.setPaintingSon((PaintingSon) itemRepository.findById(domain.getPaintingSonCode()).get());
        } else {
            throw new ResourceNotFoundException("Código do filho não encontrado: " + domain.getPaintingSonCode());
        }
        entity.setNetQuantity(domain.getNetQuantity());
        entity.setGrossQuantity(domain.getGrossQuantity());
        return entity;
    }

    @Override
    public DUsedPaintingBorderBackground toDomain(UsedPaintingBorderBackground entity) {
        DUsedPaintingBorderBackground domain = new DUsedPaintingBorderBackground();
        domain.setId(entity.getId());
        domain.setPaintingBorderBackgroundCode(entity.getPaintingBorderBackground().getCode());
        domain.setPaintingSonCode(entity.getPaintingSon().getCode());
        domain.setNetQuantity(entity.getNetQuantity());
        domain.setGrossQuantity(entity.getGrossQuantity());
        domain.setMeasurementUnit(entity.getMeasurementUnit());
        return domain;
    }
}
