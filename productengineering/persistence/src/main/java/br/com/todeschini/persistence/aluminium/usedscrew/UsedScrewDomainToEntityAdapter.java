package br.com.todeschini.persistence.aluminium.usedscrew;

import br.com.todeschini.domain.business.aluminium.usedscrew.DUsedScrew;
import br.com.todeschini.domain.exceptions.ResourceNotFoundException;
import br.com.todeschini.persistence.aluminium.screw.ScrewRepository;
import br.com.todeschini.persistence.entities.aluminium.AluminiumSon;
import br.com.todeschini.persistence.entities.aluminium.UsedScrew;
import br.com.todeschini.persistence.publico.item.ItemRepository;
import br.com.todeschini.persistence.util.Convertable;
import org.springframework.stereotype.Component;

@Component
public class UsedScrewDomainToEntityAdapter implements Convertable<UsedScrew, DUsedScrew> {

    private final ScrewRepository screwRepository;
    private final ItemRepository itemRepository;

    public UsedScrewDomainToEntityAdapter(ScrewRepository screwRepository, ItemRepository itemRepository){
        this.screwRepository = screwRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public UsedScrew toEntity(DUsedScrew domain) {
        UsedScrew entity = new UsedScrew();
        entity.setId(domain.getId());
        entity.setScrew(screwRepository.findById(domain.getScrewCode()).orElseThrow(() -> new ResourceNotFoundException("Parafuso não encontrado")));
        if(itemRepository.existsById(domain.getAluminiumSonCode())){
            entity.setAluminiumSon((AluminiumSon) itemRepository.findById(domain.getAluminiumSonCode()).get());
        } else {
            throw new ResourceNotFoundException("Código do filho não encontrado: " + domain.getAluminiumSonCode());
        }
        entity.setQuantity(domain.getQuantity());
        return entity;
    }

    @Override
    public DUsedScrew toDomain(UsedScrew entity) {
        DUsedScrew domain = new DUsedScrew();
        domain.setId(entity.getId());
        domain.setScrewCode(entity.getScrew().getCode());
        domain.setAluminiumSonCode(entity.getAluminiumSon().getCode());
        domain.setQuantity(entity.getQuantity());
        domain.setMeasurementUnit(entity.getMeasurementUnit());
        return domain;
    }
}
