package br.com.todeschini.persistence.packaging.usedcornerbracket;

import br.com.todeschini.domain.business.packaging.usedcornerbracket.DUsedCornerBracket;
import br.com.todeschini.domain.exceptions.ResourceNotFoundException;
import br.com.todeschini.persistence.entities.packaging.UsedCornerBracket;
import br.com.todeschini.persistence.packaging.cornerbracket.CornerBracketDomainToEntityAdapter;
import br.com.todeschini.persistence.packaging.cornerbracket.CornerBracketRepository;
import br.com.todeschini.persistence.packaging.ghost.GhostRepository;
import br.com.todeschini.persistence.util.Convertable;
import org.springframework.stereotype.Component;

@Component
public class UsedCornerBracketDomainToEntityAdapter implements Convertable<UsedCornerBracket, DUsedCornerBracket> {

    private final CornerBracketRepository cornerBracketRepository;
    private final GhostRepository ghostRepository;

    public UsedCornerBracketDomainToEntityAdapter(CornerBracketRepository cornerBracketRepository, GhostRepository ghostRepository){
        this.cornerBracketRepository = cornerBracketRepository;
        this.ghostRepository = ghostRepository;
    }

    @Override
    public UsedCornerBracket toEntity(DUsedCornerBracket domain) {
        UsedCornerBracket entity = new UsedCornerBracket();
        entity.setId(domain.getId());
        entity.setCornerBracket(cornerBracketRepository.findById(domain.getCornerBracketCode()).orElseThrow(() -> new ResourceNotFoundException("Cantoneira não encontrada")));
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
    public DUsedCornerBracket toDomain(UsedCornerBracket entity) {
        DUsedCornerBracket domain = new DUsedCornerBracket();
        domain.setId(entity.getId());
        domain.setCornerBracketCode(entity.getCornerBracket().getCode());
        domain.setGhostCode(entity.getGhost().getCode());
        domain.setNetQuantity(entity.getNetQuantity());
        domain.setGrossQuantity(entity.getGrossQuantity());
        domain.setMeasurementUnit(entity.getMeasurementUnit());
        return domain;
    }
}
