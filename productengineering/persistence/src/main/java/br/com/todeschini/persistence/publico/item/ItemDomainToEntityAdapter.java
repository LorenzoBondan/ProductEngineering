package br.com.todeschini.persistence.publico.item;

import br.com.todeschini.domain.business.publico.item.DItem;
import br.com.todeschini.persistence.entities.publico.Item;
import br.com.todeschini.persistence.util.Convertable;
import org.springframework.stereotype.Component;

@Component
public class ItemDomainToEntityAdapter implements Convertable<Item, DItem> {

    @Override
    public Item toEntity(DItem domain) {
        return Item.builder()
                .code(domain.getCode())
                .description(domain.getDescription())
                .measure1(domain.getMeasure1())
                .measure2(domain.getMeasure2())
                .measure3(domain.getMeasure3())
                .measurementUnit(domain.getMeasurementUnit())
                .build();
    }

    @Override
    public DItem toDomain(Item entity) {
        return DItem.builder()
                .code(entity.getCode())
                .description(entity.getDescription())
                .measure1(entity.getMeasure1())
                .measure2(entity.getMeasure2())
                .measure3(entity.getMeasure3())
                .measurementUnit(entity.getMeasurementUnit())
                .build();
    }
}
