package br.com.todeschini.domain.business.publico.color.api;

import br.com.todeschini.domain.business.publico.color.DColor;

import java.util.List;

public interface FindAllActiveColorAndCurrentOne {

    List<DColor> findAllActiveAndCurrentOne (Long id);
}
