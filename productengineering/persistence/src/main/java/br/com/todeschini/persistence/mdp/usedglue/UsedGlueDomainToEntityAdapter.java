package br.com.todeschini.persistence.mdp.usedglue;

import br.com.todeschini.domain.business.mdp.usedglue.DUsedGlue;
import br.com.todeschini.domain.exceptions.ResourceNotFoundException;
import br.com.todeschini.persistence.entities.mdp.MDPSon;
import br.com.todeschini.persistence.entities.mdp.UsedGlue;
import br.com.todeschini.persistence.mdp.glue.GlueRepository;
import br.com.todeschini.persistence.publico.item.ItemRepository;
import br.com.todeschini.persistence.util.Convertable;
import org.springframework.stereotype.Component;

@Component
public class UsedGlueDomainToEntityAdapter implements Convertable<UsedGlue, DUsedGlue> {

    private final GlueRepository glueRepository;
    private final ItemRepository itemRepository;

    public UsedGlueDomainToEntityAdapter(GlueRepository glueRepository, ItemRepository itemRepository){
        this.glueRepository = glueRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public UsedGlue toEntity(DUsedGlue domain) {
        UsedGlue entity = new UsedGlue();
        entity.setId(domain.getId());
        entity.setGlue(glueRepository.findById(domain.getGlueCode()).orElseThrow(() -> new ResourceNotFoundException("Cola não encontrada")));
        if(itemRepository.existsById(domain.getMdpSonCode())){
            entity.setSon((MDPSon) itemRepository.findById(domain.getMdpSonCode()).get());
        } else {
            throw new ResourceNotFoundException("Código do filho não encontrado: " + domain.getMdpSonCode());
        }
        entity.setNetQuantity(domain.getNetQuantity());
        entity.setGrossQuantity(domain.getGrossQuantity());
        return entity;
    }

    @Override
    public DUsedGlue toDomain(UsedGlue entity) {
        DUsedGlue domain = new DUsedGlue();
        domain.setId(entity.getId());
        domain.setGlueCode(entity.getGlue().getCode());
        domain.setMdpSonCode(entity.getSon().getCode());
        domain.setNetQuantity(entity.getNetQuantity());
        domain.setGrossQuantity(entity.getGrossQuantity());
        domain.setMeasurementUnit(entity.getMeasurementUnit());
        return domain;
    }
}
