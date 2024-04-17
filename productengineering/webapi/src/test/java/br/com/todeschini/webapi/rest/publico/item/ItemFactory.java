package br.com.todeschini.webapi.rest.publico.item;

import br.com.todeschini.domain.business.publico.item.DItem;

public class ItemFactory {

    public static DItem createDItem() {
        DItem item = new DItem();
        item.setCode(1L);
        item.setDescription("Item");
        item.setMeasure1(1);
        item.setMeasure2(1);
        item.setMeasure3(1);
        item.setMeasurementUnit("UN");
        return item;
    }

    public static DItem createDuplicatedDItem() {
        DItem item = new DItem();
        item.setCode(2L);
        item.setDescription("Item");
        item.setMeasure1(1);
        item.setMeasure2(1);
        item.setMeasure3(1);
        item.setMeasurementUnit("UN");
        return item;
    }

    public static DItem createNonExistingDItem(Long nonExistingId) {
        DItem item = new DItem();
        item.setCode(nonExistingId);
        item.setDescription("Item");
        item.setMeasure1(1);
        item.setMeasure2(1);
        item.setMeasure3(1);
        item.setMeasurementUnit("UN");
        return item;
    }
}
