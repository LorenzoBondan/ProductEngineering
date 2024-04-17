package br.com.todeschini.domain.business.publico.item.api;

import br.com.todeschini.domain.business.publico.item.DItem;

import java.util.List;

public interface FindAllActiveItemAndCurrentOne {

    List<DItem> findAllActiveAndCurrentOne (Long id);
}
