package br.com.todeschini.domain.business.packaging.usedcornerbracket.spi;

import br.com.todeschini.domain.SimpleCrud;
import br.com.todeschini.domain.business.packaging.usedcornerbracket.DUsedCornerBracket;

import java.util.Collection;

public interface CrudUsedCornerBracket extends SimpleCrud<DUsedCornerBracket, Long> {

    Collection<? extends DUsedCornerBracket> findByCornerBracketAndGhost (Long cornerBracketId, String ghostId);
}
