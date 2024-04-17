package br.com.todeschini.persistence.mdf.usedbacksheet;

import br.com.todeschini.domain.business.mdf.usedbacksheet.DUsedBackSheet;
import br.com.todeschini.domain.exceptions.ResourceNotFoundException;
import br.com.todeschini.persistence.entities.mdf.UsedBackSheet;
import br.com.todeschini.persistence.mdf.back.BackRepository;
import br.com.todeschini.persistence.mdp.sheet.SheetRepository;
import br.com.todeschini.persistence.util.Convertable;
import org.springframework.stereotype.Component;

@Component
public class UsedBackSheetDomainToEntityAdapter implements Convertable<UsedBackSheet, DUsedBackSheet> {

    private final BackRepository backRepository;
    private final SheetRepository sheetRepository;

    public UsedBackSheetDomainToEntityAdapter(BackRepository backRepository, SheetRepository sheetRepository){
        this.backRepository = backRepository;
        this.sheetRepository = sheetRepository;
    }

    @Override
    public UsedBackSheet toEntity(DUsedBackSheet domain) {
        UsedBackSheet entity = new UsedBackSheet();
        entity.setId(domain.getId());
        entity.setBack(backRepository.findById(domain.getBackCode()).orElseThrow(() -> new ResourceNotFoundException("Fundo não encontrado")));
        entity.setSheet(sheetRepository.findById(domain.getSheetCode()).orElseThrow(() -> new ResourceNotFoundException("Chapa não encontrada")));
        entity.setNetQuantity(domain.getNetQuantity());
        entity.setGrossQuantity(domain.getGrossQuantity());
        return entity;
    }

    @Override
    public DUsedBackSheet toDomain(UsedBackSheet entity) {
        DUsedBackSheet domain = new DUsedBackSheet();
        domain.setId(entity.getId());
        domain.setBackCode(entity.getBack().getCode());
        domain.setSheetCode(entity.getSheet().getCode());
        domain.setNetQuantity(entity.getNetQuantity());
        domain.setGrossQuantity(entity.getGrossQuantity());
        domain.setMeasurementUnit(entity.getMeasurementUnit());
        return domain;
    }
}
