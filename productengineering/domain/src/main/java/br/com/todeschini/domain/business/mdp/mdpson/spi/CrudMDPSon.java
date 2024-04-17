package br.com.todeschini.domain.business.mdp.mdpson.spi;

import br.com.todeschini.domain.SimpleCrud;
import br.com.todeschini.domain.business.mdp.mdpson.DMDPSon;

import java.util.Collection;

public interface CrudMDPSon extends SimpleCrud<DMDPSon, Long> {

    Collection<? extends DMDPSon> findByDescription (String description);
}
