package br.com.todeschini.persistence.mdf.usedpainting;

import br.com.todeschini.domain.business.mdf.usedpainting.DUsedPainting;
import br.com.todeschini.domain.exceptions.ResourceNotFoundException;
import br.com.todeschini.persistence.entities.mdf.PaintingSon;
import br.com.todeschini.persistence.entities.mdf.UsedPainting;
import br.com.todeschini.persistence.mdf.painting.PaintingRepository;
import br.com.todeschini.persistence.publico.item.ItemRepository;
import br.com.todeschini.persistence.util.Convertable;
import org.springframework.stereotype.Component;

@Component
public class UsedPaintingDomainToEntityAdapter implements Convertable<UsedPainting, DUsedPainting> {

    private final PaintingRepository paintingRepository;
    private final ItemRepository itemRepository;

    public UsedPaintingDomainToEntityAdapter(PaintingRepository paintingRepository, ItemRepository itemRepository){
        this.paintingRepository = paintingRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public UsedPainting toEntity(DUsedPainting domain) {
        UsedPainting entity = new UsedPainting();
        entity.setId(domain.getId());
        entity.setPainting(paintingRepository.findById(domain.getPaintingCode()).orElseThrow(() -> new ResourceNotFoundException("Pintura não encontrada")));
        if(itemRepository.existsById(domain.getPaintingSonCode())){
            entity.setPaintingSon((PaintingSon) itemRepository.findById(domain.getPaintingSonCode()).get());
        } else {
            throw new ResourceNotFoundException("Código do filho não encontrado: " + domain.getPaintingCode());
        }
        entity.setNetQuantity(domain.getNetQuantity());
        entity.setGrossQuantity(domain.getGrossQuantity());
        return entity;
    }

    @Override
    public DUsedPainting toDomain(UsedPainting entity) {
        DUsedPainting domain = new DUsedPainting();
        domain.setId(entity.getId());
        domain.setPaintingCode(entity.getPainting().getCode());
        domain.setPaintingSonCode(entity.getPaintingSon().getCode());
        domain.setNetQuantity(entity.getNetQuantity());
        domain.setGrossQuantity(entity.getGrossQuantity());
        domain.setMeasurementUnit(entity.getMeasurementUnit());
        return domain;
    }
}
