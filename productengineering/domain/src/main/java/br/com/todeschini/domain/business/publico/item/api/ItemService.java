package br.com.todeschini.domain.business.publico.item.api;

public interface ItemService extends FindItem, InsertItem, UpdateItem, DeleteItem, InactivateItem,
        FindAllActiveItemAndCurrentOne {
}
