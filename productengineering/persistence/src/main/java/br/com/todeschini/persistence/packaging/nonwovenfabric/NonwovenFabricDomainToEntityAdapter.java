package br.com.todeschini.persistence.packaging.nonwovenfabric;

import br.com.todeschini.domain.business.packaging.nonwovenfabric.DNonwovenFabric;
import br.com.todeschini.persistence.entities.packaging.NonwovenFabric;
import br.com.todeschini.persistence.util.Convertable;
import org.springframework.stereotype.Component;

@Component
public class NonwovenFabricDomainToEntityAdapter implements Convertable<NonwovenFabric, DNonwovenFabric> {

    @Override
    public NonwovenFabric toEntity(DNonwovenFabric domain) {
        NonwovenFabric entity = new NonwovenFabric();
        entity.setCode(domain.getCode());
        entity.setDescription(domain.getDescription().toUpperCase());
        entity.setFamily(domain.getFamily());
        entity.setImplementation(domain.getImplementation());
        entity.setLostPercentage(domain.getLostPercentage());
        entity.setValue(domain.getValue());
        return entity;
    }

    @Override
    public DNonwovenFabric toDomain(NonwovenFabric entity) {
        DNonwovenFabric domain = new DNonwovenFabric();
        domain.setCode(entity.getCode());
        domain.setDescription(entity.getDescription().toUpperCase());
        domain.setFamily(entity.getFamily());
        domain.setImplementation(entity.getImplementation());
        domain.setLostPercentage(entity.getLostPercentage());
        domain.setValue(entity.getValue());
        return domain;
    }
}
