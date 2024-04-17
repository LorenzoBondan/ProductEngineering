package br.com.todeschini.domain.business.publico.item.api;

import br.com.todeschini.domain.business.publico.item.DItem;

public interface UpdateItem {

    DItem update (Long id, DItem item);
}
