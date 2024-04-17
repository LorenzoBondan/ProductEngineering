package br.com.todeschini.domain.business.aluminium.aluminiumson.api;

import br.com.todeschini.domain.business.aluminium.aluminiumson.DAluminiumSon;

import java.util.List;

public interface FindAllActiveAluminiumSonAndCurrentOne {

    List<DAluminiumSon> findAllActiveAndCurrentOne (Long id);
}
