package br.com.todeschini.domain.business.aluminium.aluminiumtype.api;

import br.com.todeschini.domain.business.aluminium.aluminiumtype.DAluminiumType;

import java.util.List;

public interface FindAllActiveAluminiumTypeAndCurrentOne {

    List<DAluminiumType> findAllActiveAndCurrentOne (Long id);
}
