package br.com.todeschini.persistence.mdp.sheet;

import br.com.todeschini.domain.business.mdp.sheet.DSheet;
import br.com.todeschini.persistence.entities.mdp.Sheet;
import br.com.todeschini.persistence.publico.color.ColorDomainToEntityAdapter;
import br.com.todeschini.persistence.publico.color.ColorRepository;
import br.com.todeschini.persistence.publico.material.MaterialRepository;
import br.com.todeschini.persistence.util.Convertable;
import org.springframework.stereotype.Component;

@Component
public class SheetDomainToEntityAdapter implements Convertable<Sheet, DSheet> {

    private final ColorRepository colorRepository;
    private final ColorDomainToEntityAdapter colorAdapter;
    private final MaterialRepository materialRepository;

    public SheetDomainToEntityAdapter(ColorRepository colorRepository, ColorDomainToEntityAdapter colorAdapter, MaterialRepository materialRepository) {
        this.colorRepository = colorRepository;
        this.colorAdapter = colorAdapter;
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
        entity.setColor(colorRepository.findById(domain.getColor().getCode()).get());
        entity.setMaterial(materialRepository.findById(domain.getMaterialId()).get());
        entity.setValue(domain.getValue());
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
        domain.setColor(colorAdapter.toDomain(entity.getColor()));
        domain.setMaterialId(entity.getMaterial().getId());
        domain.setValue(entity.getValue());
        return domain;
    }
}
