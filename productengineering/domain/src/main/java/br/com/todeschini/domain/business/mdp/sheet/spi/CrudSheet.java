package br.com.todeschini.domain.business.mdp.sheet.spi;

import br.com.todeschini.domain.SimpleCrud;
import br.com.todeschini.domain.business.mdp.sheet.DSheet;

import java.util.Collection;

public interface CrudSheet extends SimpleCrud<DSheet, Long> {

    Collection<? extends DSheet> findByDescription (String description);
}
