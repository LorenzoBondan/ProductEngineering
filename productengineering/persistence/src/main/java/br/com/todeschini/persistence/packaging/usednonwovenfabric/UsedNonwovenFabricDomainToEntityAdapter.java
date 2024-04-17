package br.com.todeschini.persistence.packaging.usednonwovenfabric;

import br.com.todeschini.domain.business.packaging.usednonwovenfabric.DUsedNonwovenFabric;
import br.com.todeschini.domain.exceptions.ResourceNotFoundException;
import br.com.todeschini.persistence.entities.packaging.UsedNonwovenFabric;
import br.com.todeschini.persistence.packaging.ghost.GhostRepository;
import br.com.todeschini.persistence.packaging.nonwovenfabric.NonwovenFabricRepository;
import br.com.todeschini.persistence.util.Convertable;
import org.springframework.stereotype.Component;

@Component
public class UsedNonwovenFabricDomainToEntityAdapter implements Convertable<UsedNonwovenFabric, DUsedNonwovenFabric> {

    private final NonwovenFabricRepository nonwovenFabricRepository;
    private final GhostRepository ghostRepository;

    public UsedNonwovenFabricDomainToEntityAdapter(NonwovenFabricRepository nonwovenFabricRepository, GhostRepository ghostRepository){
        this.nonwovenFabricRepository = nonwovenFabricRepository;
        this.ghostRepository = ghostRepository;
    }

    @Override
    public UsedNonwovenFabric toEntity(DUsedNonwovenFabric domain) {
        UsedNonwovenFabric entity = new UsedNonwovenFabric();
        entity.setId(domain.getId());
        entity.setNonwovenFabric(nonwovenFabricRepository.findById(domain.getNonwovenFabricCode()).orElseThrow(() -> new ResourceNotFoundException("Poliéster não encontrado")));
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
    public DUsedNonwovenFabric toDomain(UsedNonwovenFabric entity) {
        DUsedNonwovenFabric domain = new DUsedNonwovenFabric();
        domain.setId(entity.getId());
        domain.setNonwovenFabricCode(entity.getNonwovenFabric().getCode());
        domain.setGhostCode(entity.getGhost().getCode());
        domain.setNetQuantity(entity.getNetQuantity());
        domain.setGrossQuantity(entity.getGrossQuantity());
        domain.setMeasurementUnit(entity.getMeasurementUnit());
        return domain;
    }
}
