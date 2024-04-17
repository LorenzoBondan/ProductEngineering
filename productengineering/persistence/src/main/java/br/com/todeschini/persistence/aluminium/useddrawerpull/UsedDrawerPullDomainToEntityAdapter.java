package br.com.todeschini.persistence.aluminium.useddrawerpull;

import br.com.todeschini.domain.business.aluminium.useddrawerpull.DUsedDrawerPull;
import br.com.todeschini.domain.exceptions.ResourceNotFoundException;
import br.com.todeschini.persistence.aluminium.drawerpull.DrawerPullRepository;
import br.com.todeschini.persistence.entities.aluminium.AluminiumSon;
import br.com.todeschini.persistence.entities.aluminium.UsedDrawerPull;
import br.com.todeschini.persistence.publico.item.ItemRepository;
import br.com.todeschini.persistence.util.Convertable;
import org.springframework.stereotype.Component;

@Component
public class UsedDrawerPullDomainToEntityAdapter implements Convertable<UsedDrawerPull, DUsedDrawerPull> {

    private final DrawerPullRepository drawerPullRepository;
    private final ItemRepository itemRepository;

    public UsedDrawerPullDomainToEntityAdapter(DrawerPullRepository drawerPullRepository, ItemRepository itemRepository){
        this.drawerPullRepository = drawerPullRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public UsedDrawerPull toEntity(DUsedDrawerPull domain) {
        UsedDrawerPull entity = new UsedDrawerPull();
        entity.setId(domain.getId());
        entity.setDrawerPull(drawerPullRepository.findById(domain.getDrawerPullCode()).orElseThrow(() -> new ResourceNotFoundException("Puxador não encontrado")));
        entity.setAluminiumSon((AluminiumSon) itemRepository.findById(domain.getAluminiumSonCode()).orElseThrow(() -> new ResourceNotFoundException("Código do filho não encontrado: " + domain.getAluminiumSonCode())));
        entity.setQuantity(domain.getQuantity());
        return entity;
    }

    @Override
    public DUsedDrawerPull toDomain(UsedDrawerPull entity) {
        DUsedDrawerPull domain = new DUsedDrawerPull();
        domain.setId(entity.getId());
        domain.setDrawerPullCode(entity.getDrawerPull().getCode());
        domain.setAluminiumSonCode(entity.getAluminiumSon().getCode());
        domain.setQuantity(entity.getQuantity());
        domain.setMeasurementUnit(entity.getMeasurementUnit());
        return domain;
    }
}
