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
        entity.setDescription(domain.getDescription());
        entity.setFamily(domain.getFamily());
        entity.setImplementation(domain.getImplementation());
        entity.setLostPercentage(domain.getLostPercentage());
        return entity;
    }

    @Override
    public DCornerBracket toDomain(CornerBracket entity) {
        DCornerBracket domain = new DCornerBracket();
        domain.setCode(entity.getCode());
        domain.setDescription(entity.getDescription());
        domain.setFamily(entity.getFamily());
        domain.setImplementation(entity.getImplementation());
        domain.setLostPercentage(entity.getLostPercentage());
        return domain;
    }
}
