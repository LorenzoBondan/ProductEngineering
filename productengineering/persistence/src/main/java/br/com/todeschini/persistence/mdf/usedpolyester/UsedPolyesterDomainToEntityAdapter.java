package br.com.todeschini.persistence.mdf.usedpolyester;

import br.com.todeschini.domain.business.mdf.usedpolyester.DUsedPolyester;
import br.com.todeschini.domain.exceptions.ResourceNotFoundException;
import br.com.todeschini.persistence.entities.mdf.PaintingSon;
import br.com.todeschini.persistence.entities.mdf.UsedPolyester;
import br.com.todeschini.persistence.mdf.polyester.PolyesterRepository;
import br.com.todeschini.persistence.publico.item.ItemRepository;
import br.com.todeschini.persistence.util.Convertable;
import org.springframework.stereotype.Component;

@Component
public class UsedPolyesterDomainToEntityAdapter implements Convertable<UsedPolyester, DUsedPolyester> {

    private final PolyesterRepository polyesterRepository;
    private final ItemRepository itemRepository;

    public UsedPolyesterDomainToEntityAdapter(PolyesterRepository polyesterRepository, ItemRepository itemRepository){
        this.polyesterRepository = polyesterRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public UsedPolyester toEntity(DUsedPolyester domain) {
        UsedPolyester entity = new UsedPolyester();
        entity.setId(domain.getId());
        entity.setPolyester(polyesterRepository.findById(domain.getPolyesterCode()).orElseThrow(() -> new ResourceNotFoundException("Poliéster não encontrado")));
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
    public DUsedPolyester toDomain(UsedPolyester entity) {
        DUsedPolyester domain = new DUsedPolyester();
        domain.setId(entity.getId());
        domain.setPolyesterCode(entity.getPolyester().getCode());
        domain.setPaintingSonCode(entity.getPaintingSon().getCode());
        domain.setNetQuantity(entity.getNetQuantity());
        domain.setGrossQuantity(entity.getGrossQuantity());
        domain.setMeasurementUnit(entity.getMeasurementUnit());
        return domain;
    }
}
