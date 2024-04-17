package br.com.todeschini.domain.business.packaging.usedplastic.spi;

import br.com.todeschini.domain.SimpleCrud;
import br.com.todeschini.domain.business.packaging.usedplastic.DUsedPlastic;

import java.util.Collection;

public interface CrudUsedPlastic extends SimpleCrud<DUsedPlastic, Long> {

    Collection<? extends DUsedPlastic> findByPlasticAndGhost (Long plasticId, String ghostId);
}
