package br.com.todeschini.persistence.packaging.usedplastic;

import br.com.todeschini.domain.business.packaging.usedplastic.DUsedPlastic;
import br.com.todeschini.domain.exceptions.ResourceNotFoundException;
import br.com.todeschini.persistence.entities.packaging.UsedPlastic;
import br.com.todeschini.persistence.packaging.ghost.GhostRepository;
import br.com.todeschini.persistence.packaging.plastic.PlasticRepository;
import br.com.todeschini.persistence.util.Convertable;
import org.springframework.stereotype.Component;

@Component
public class UsedPlasticDomainToEntityAdapter implements Convertable<UsedPlastic, DUsedPlastic> {

    private final PlasticRepository plasticRepository;
    private final GhostRepository ghostRepository;

    public UsedPlasticDomainToEntityAdapter(PlasticRepository plasticRepository, GhostRepository ghostRepository){
        this.plasticRepository = plasticRepository;
        this.ghostRepository = ghostRepository;
    }

    @Override
    public UsedPlastic toEntity(DUsedPlastic domain) {
        UsedPlastic entity = new UsedPlastic();
        entity.setId(domain.getId());
        entity.setPlastic(plasticRepository.findById(domain.getPlasticCode()).orElseThrow(() -> new ResourceNotFoundException("Plástico não encontrado")));
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
    public DUsedPlastic toDomain(UsedPlastic entity) {
        DUsedPlastic domain = new DUsedPlastic();
        domain.setId(entity.getId());
        domain.setPlasticCode(entity.getPlastic().getCode());
        domain.setGhostCode(entity.getGhost().getCode());
        domain.setNetQuantity(entity.getNetQuantity());
        domain.setGrossQuantity(entity.getGrossQuantity());
        domain.setMeasurementUnit(entity.getMeasurementUnit());
        return domain;
    }
}
