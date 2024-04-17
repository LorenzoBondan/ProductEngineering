package br.com.todeschini.persistence.mdp.sheet;

import br.com.todeschini.domain.business.mdp.sheet.DSheet;
import br.com.todeschini.persistence.entities.mdp.Sheet;
import br.com.todeschini.persistence.publico.material.MaterialRepository;
import br.com.todeschini.persistence.util.Convertable;
import org.springframework.stereotype.Component;

@Component
public class SheetDomainToEntityAdapter implements Convertable<Sheet, DSheet> {

    private final MaterialRepository materialRepository;

    public SheetDomainToEntityAdapter(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    @Override
    public Sheet toEntity(DSheet domain) {
        Sheet entity = new Sheet();
        entity.setCode(domain.getCode());
        entity.setDescription(domain.getDescription());
        entity.setFamily(domain.getFamily());
        entity.setImplementation(domain.getImplementation());
        entity.setLostPercentage(domain.getLostPercentage());
        entity.setThickness(domain.getThickness());
        entity.setFaces(domain.getFaces());
        entity.setThickness(domain.getThickness());
        entity.setMaterial(materialRepository.findById(domain.getMaterialId()).get());
        return entity;
    }

    @Override
    public DSheet toDomain(Sheet entity) {
        DSheet domain = new DSheet();
        domain.setCode(entity.getCode());
        domain.setDescription(entity.getDescription());
        domain.setFamily(entity.getFamily());
        domain.setImplementation(entity.getImplementation());
        domain.setLostPercentage(entity.getLostPercentage());
        domain.setThickness(entity.getThickness());
        domain.setFaces(entity.getFaces());
        domain.setThickness(entity.getThickness());
        domain.setMaterialId(entity.getMaterial().getId());
        return domain;
    }
}
