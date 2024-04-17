package br.com.todeschini.persistence.aluminium.usedmolding;

import br.com.todeschini.domain.business.aluminium.usedmolding.DUsedMolding;
import br.com.todeschini.domain.exceptions.ResourceNotFoundException;
import br.com.todeschini.persistence.aluminium.molding.MoldingRepository;
import br.com.todeschini.persistence.entities.aluminium.AluminiumSon;
import br.com.todeschini.persistence.entities.aluminium.UsedMolding;
import br.com.todeschini.persistence.publico.item.ItemRepository;
import br.com.todeschini.persistence.util.Convertable;
import org.springframework.stereotype.Component;

@Component
public class UsedMoldingDomainToEntityAdapter implements Convertable<UsedMolding, DUsedMolding> {

    private final MoldingRepository moldingRepository;
    private final ItemRepository itemRepository;

    public UsedMoldingDomainToEntityAdapter(MoldingRepository moldingRepository, ItemRepository itemRepository){
        this.moldingRepository = moldingRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public UsedMolding toEntity(DUsedMolding domain) {
        UsedMolding entity = new UsedMolding();
        entity.setId(domain.getId());
        entity.setMolding(moldingRepository.findById(domain.getMoldingCode()).orElseThrow(() -> new ResourceNotFoundException("Perfil não encontrado")));
        if(itemRepository.existsById(domain.getAluminiumSonCode())){
            entity.setAluminiumSon((AluminiumSon) itemRepository.findById(domain.getAluminiumSonCode()).get());
        } else {
            throw new ResourceNotFoundException("Código do filho não encontrado: " + domain.getAluminiumSonCode());
        }
        entity.setQuantity(domain.getQuantity());
        return entity;
    }

    @Override
    public DUsedMolding toDomain(UsedMolding entity) {
        DUsedMolding domain = new DUsedMolding();
        domain.setId(entity.getId());
        domain.setMoldingCode(entity.getMolding().getCode());
        domain.setAluminiumSonCode(entity.getAluminiumSon().getCode());
        domain.setQuantity(entity.getQuantity());
        domain.setMeasurementUnit(entity.getMeasurementUnit());
        return domain;
    }
}
