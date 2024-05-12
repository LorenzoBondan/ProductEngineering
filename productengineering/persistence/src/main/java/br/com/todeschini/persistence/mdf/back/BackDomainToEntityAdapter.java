package br.com.todeschini.persistence.mdf.back;

import br.com.todeschini.domain.business.mdf.back.DBack;
import br.com.todeschini.persistence.entities.mdf.Back;
import br.com.todeschini.persistence.mdf.usedbacksheet.UsedBackSheetDomainToEntityAdapter;
import br.com.todeschini.persistence.mdf.usedbacksheet.UsedBackSheetRepository;
import br.com.todeschini.persistence.util.Convertable;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class BackDomainToEntityAdapter implements Convertable<Back, DBack> {

    private final UsedBackSheetRepository usedBackSheetRepository;
    private final UsedBackSheetDomainToEntityAdapter usedBackSheetAdapter;

    public BackDomainToEntityAdapter(UsedBackSheetRepository usedBackSheetRepository, UsedBackSheetDomainToEntityAdapter usedBackSheetAdapter) {
        this.usedBackSheetRepository = usedBackSheetRepository;
        this.usedBackSheetAdapter = usedBackSheetAdapter;
    }

    @Override
    public Back toEntity(DBack domain) {
        Back entity = new Back();
        entity.setCode(domain.getCode());
        entity.setDescription(domain.getDescription().toUpperCase());
        entity.setFamily(domain.getFamily());
        entity.setImplementation(domain.getImplementation());
        entity.setLostPercentage(domain.getLostPercentage());
        entity.setThickness(domain.getThickness());
        entity.setSuffix(domain.getSuffix());
        entity.setMeasure1(domain.getMeasure1());
        entity.setMeasure2(domain.getMeasure2());

        entity.setSheets(domain.getUsedBackSheets().stream().map(usedBackSheet -> usedBackSheetRepository.findById(usedBackSheet.getId()).get()).collect(Collectors.toList()));
        entity.setValue(domain.getValue());
        return entity;
    }

    @Override
    public DBack toDomain(Back entity) {
        DBack domain = new DBack();
        domain.setCode(entity.getCode());
        domain.setDescription(entity.getDescription().toUpperCase());
        domain.setFamily(entity.getFamily());
        domain.setImplementation(entity.getImplementation());
        domain.setLostPercentage(entity.getLostPercentage());
        domain.setThickness(entity.getThickness());
        domain.setSuffix(entity.getSuffix());
        domain.setMeasure1(entity.getMeasure1());
        domain.setMeasure2(entity.getMeasure2());

        domain.setUsedBackSheets(entity.getSheets().stream().map(usedBackSheetAdapter::toDomain).collect(Collectors.toList()));

        domain.setValue(Math.round(entity.calculateValue() * 1e2) / 1e2);
        return domain;
    }
}
