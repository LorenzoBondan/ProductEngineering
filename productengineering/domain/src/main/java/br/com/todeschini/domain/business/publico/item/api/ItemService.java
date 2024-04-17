package br.com.todeschini.domain.business.publico.item.api;

import org.springframework.stereotype.Component;

@Component
public interface ItemService extends FindItem, InsertItem, UpdateItem, DeleteItem, InactivateItem,
        FindAllActiveItemAndCurrentOne {
}
