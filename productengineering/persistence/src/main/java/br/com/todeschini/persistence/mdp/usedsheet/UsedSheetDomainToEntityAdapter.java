package br.com.todeschini.persistence.mdp.usedsheet;

import br.com.todeschini.domain.business.mdp.usedsheet.DUsedSheet;
import br.com.todeschini.domain.exceptions.ResourceNotFoundException;
import br.com.todeschini.persistence.entities.mdp.MDPSon;
import br.com.todeschini.persistence.entities.mdp.UsedSheet;
import br.com.todeschini.persistence.mdp.sheet.SheetRepository;
import br.com.todeschini.persistence.publico.item.ItemRepository;
import br.com.todeschini.persistence.util.Convertable;
import org.springframework.stereotype.Component;

@Component
public class UsedSheetDomainToEntityAdapter implements Convertable<UsedSheet, DUsedSheet> {

    private final SheetRepository sheetRepository;
    private final ItemRepository itemRepository;

    public UsedSheetDomainToEntityAdapter(SheetRepository sheetRepository, ItemRepository itemRepository){
        this.sheetRepository = sheetRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public UsedSheet toEntity(DUsedSheet domain) {
        UsedSheet entity = new UsedSheet();
        entity.setId(domain.getId());
        entity.setSheet(sheetRepository.findById(domain.getSheetCode()).orElseThrow(() -> new ResourceNotFoundException("Chapa não encontrada")));
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
    public DUsedSheet toDomain(UsedSheet entity) {
        DUsedSheet domain = new DUsedSheet();
        domain.setId(entity.getId());
        domain.setSheetCode(entity.getSheet().getCode());
        domain.setMdpSonCode(entity.getSon().getCode());
        domain.setNetQuantity(entity.getNetQuantity());
        domain.setGrossQuantity(entity.getGrossQuantity());
        domain.setMeasurementUnit(entity.getMeasurementUnit());
        return domain;
    }
}
