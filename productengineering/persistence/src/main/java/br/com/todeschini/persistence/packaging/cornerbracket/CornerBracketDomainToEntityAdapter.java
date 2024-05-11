package br.com.todeschini.persistence.packaging.cornerbracket;

import br.com.todeschini.domain.business.packaging.cornerbracket.DCornerBracket;
import br.com.todeschini.persistence.entities.packaging.CornerBracket;
import br.com.todeschini.persistence.util.Convertable;
import org.springframework.stereotype.Component;

@Component
public class CornerBracketDomainToEntityAdapter implements Convertable<CornerBracket, DCornerBracket> {

    @Override
    public CornerBracket toEntity(DCornerBracket domain) {
        CornerBracket entity = new CornerBracket();
        entity.setCode(domain.getCode());
        entity.setDescription(domain.getDescription().toUpperCase());
        entity.setFamily(domain.getFamily());
        entity.setImplementation(domain.getImplementation());
        entity.setLostPercentage(domain.getLostPercentage());
        entity.setValue(domain.getValue());
        return entity;
    }

    @Override
    public DCornerBracket toDomain(CornerBracket entity) {
        DCornerBracket domain = new DCornerBracket();
        domain.setCode(entity.getCode());
        domain.setDescription(entity.getDescription().toUpperCase());
        domain.setFamily(entity.getFamily());
        domain.setImplementation(entity.getImplementation());
        domain.setLostPercentage(entity.getLostPercentage());
        domain.setValue(entity.getValue());
        return domain;
    }
}
