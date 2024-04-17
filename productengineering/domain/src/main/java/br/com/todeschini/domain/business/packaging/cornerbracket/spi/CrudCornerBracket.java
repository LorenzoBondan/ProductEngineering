package br.com.todeschini.domain.business.packaging.cornerbracket.spi;

import br.com.todeschini.domain.SimpleCrud;
import br.com.todeschini.domain.business.packaging.cornerbracket.DCornerBracket;

import java.util.Collection;

public interface CrudCornerBracket extends SimpleCrud<DCornerBracket, Long> {

    Collection<? extends DCornerBracket> findByDescription (String description);
}
