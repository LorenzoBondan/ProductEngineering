package br.com.todeschini.persistence.publico.color;

import br.com.todeschini.domain.business.publico.color.DColor;
import br.com.todeschini.persistence.entities.publico.Color;
import br.com.todeschini.persistence.util.Convertable;
import org.springframework.stereotype.Component;

@Component
public class ColorDomainToEntityAdapter implements Convertable<Color, DColor> {

    @Override
    public Color toEntity(DColor domain) {
        return Color.builder()
                .code(domain.getCode())
                .name(domain.getName())
                .build();
    }

    @Override
    public DColor toDomain(Color entity) {
        return DColor.builder()
                .code(entity.getCode())
                .name(entity.getName())
                .build();
    }
}
