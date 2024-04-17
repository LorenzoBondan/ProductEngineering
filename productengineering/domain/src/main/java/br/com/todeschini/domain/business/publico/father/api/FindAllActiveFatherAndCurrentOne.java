package br.com.todeschini.domain.business.publico.father.api;

import br.com.todeschini.domain.business.publico.father.DFather;

import java.util.List;

public interface FindAllActiveFatherAndCurrentOne {

    List<DFather> findAllActiveAndCurrentOne (Long id);
}
