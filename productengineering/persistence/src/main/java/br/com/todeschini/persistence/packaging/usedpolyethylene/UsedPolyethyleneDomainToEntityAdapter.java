package br.com.todeschini.persistence.packaging.usedpolyethylene;

import br.com.todeschini.domain.business.packaging.usedpolyethylene.DUsedPolyethylene;
import br.com.todeschini.domain.exceptions.ResourceNotFoundException;
import br.com.todeschini.persistence.entities.packaging.UsedPolyethylene;
import br.com.todeschini.persistence.packaging.ghost.GhostRepository;
import br.com.todeschini.persistence.packaging.polyethylene.PolyethyleneRepository;
import br.com.todeschini.persistence.util.Convertable;
import org.springframework.stereotype.Component;

@Component
public class UsedPolyethyleneDomainToEntityAdapter implements Convertable<UsedPolyethylene, DUsedPolyethylene> {

    private final PolyethyleneRepository polyethyleneRepository;
    private final GhostRepository ghostRepository;

    public UsedPolyethyleneDomainToEntityAdapter(PolyethyleneRepository polyethyleneRepository, GhostRepository ghostRepository){
        this.polyethyleneRepository = polyethyleneRepository;
        this.ghostRepository = ghostRepository;
    }

    @Override
    public UsedPolyethylene toEntity(DUsedPolyethylene domain) {
        UsedPolyethylene entity = new UsedPolyethylene();
        entity.setId(domain.getId());
        entity.setPolyethylene(polyethyleneRepository.findById(domain.getPolyethyleneCode()).orElseThrow(() -> new ResourceNotFoundException("Polietileno não encontrado")));
        if(ghostRepository.existsByCode(domain.getGhostCode())){
            entity.setGhost(ghostRepository.findByCode(domain.getGhostCode()));
        } else {
            throw new ResourceNotFoundException("Código do fantasma não encontrado: " + domain.getGhostCode());
        }
        entity.setNetQuantity(domain.getNetQuantity());
        entity.setGrossQuantity(domain.getGrossQuantity());
        return entity;
    }

    @Override
    public DUsedPolyethylene toDomain(UsedPolyethylene entity) {
        DUsedPolyethylene domain = new DUsedPolyethylene();
        domain.setId(entity.getId());
        domain.setPolyethyleneCode(entity.getPolyethylene().getCode());
        domain.setGhostCode(entity.getGhost().getCode());
        domain.setNetQuantity(entity.getNetQuantity());
        domain.setGrossQuantity(entity.getGrossQuantity());
        domain.setMeasurementUnit(entity.getMeasurementUnit());
        return domain;
    }
}
