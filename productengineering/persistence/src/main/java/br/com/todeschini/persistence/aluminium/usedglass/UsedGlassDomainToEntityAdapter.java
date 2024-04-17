package br.com.todeschini.persistence.aluminium.usedglass;

import br.com.todeschini.domain.business.aluminium.usedglass.DUsedGlass;
import br.com.todeschini.domain.exceptions.ResourceNotFoundException;
import br.com.todeschini.persistence.aluminium.glass.GlassRepository;
import br.com.todeschini.persistence.entities.aluminium.AluminiumSon;
import br.com.todeschini.persistence.entities.aluminium.UsedGlass;
import br.com.todeschini.persistence.publico.item.ItemRepository;
import br.com.todeschini.persistence.util.Convertable;
import org.springframework.stereotype.Component;

@Component
public class UsedGlassDomainToEntityAdapter implements Convertable<UsedGlass, DUsedGlass> {

    private final GlassRepository glassRepository;
    private final ItemRepository itemRepository;

    public UsedGlassDomainToEntityAdapter(GlassRepository glassRepository, ItemRepository itemRepository){
        this.glassRepository = glassRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public UsedGlass toEntity(DUsedGlass domain) {
        UsedGlass entity = new UsedGlass();
        entity.setId(domain.getId());
        entity.setGlass(glassRepository.findById(domain.getGlassCode()).orElseThrow(() -> new ResourceNotFoundException("Vidro não encontrado")));
        if(itemRepository.existsById(domain.getAluminiumSonCode())){
            entity.setAluminiumSon((AluminiumSon) itemRepository.findById(domain.getAluminiumSonCode()).get());
        } else {
            throw new ResourceNotFoundException("Código do filho não encontrado: " + domain.getAluminiumSonCode());
        }
        entity.setQuantity(domain.getQuantity());
        return entity;
    }

    @Override
    public DUsedGlass toDomain(UsedGlass entity) {
        DUsedGlass domain = new DUsedGlass();
        domain.setId(entity.getId());
        domain.setGlassCode(entity.getGlass().getCode());
        domain.setAluminiumSonCode(entity.getAluminiumSon().getCode());
        domain.setQuantity(entity.getQuantity());
        domain.setMeasurementUnit(entity.getMeasurementUnit());
        return domain;
    }
}
