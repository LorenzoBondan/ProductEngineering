package br.com.todeschini.persistence.aluminium.aluminiumtype;

import br.com.todeschini.domain.business.aluminium.aluminiumtype.DAluminiumType;
import br.com.todeschini.persistence.entities.aluminium.AluminiumType;
import br.com.todeschini.persistence.util.Convertable;
import org.springframework.stereotype.Component;

@Component
public class AluminiumTypeDomainToEntityAdapter implements Convertable<AluminiumType, DAluminiumType> {

    @Override
    public AluminiumType toEntity(DAluminiumType domain) {
        return AluminiumType.builder()
                .id(domain.getId())
                .name(domain.getName())
                .lessQuantity(domain.getLessQuantity())
                .build();
    }

    @Override
    public DAluminiumType toDomain(AluminiumType entity) {
        return DAluminiumType.builder()
                .id(entity.getId())
                .name(entity.getName())
                .lessQuantity(entity.getLessQuantity())
                .build();
    }
}
